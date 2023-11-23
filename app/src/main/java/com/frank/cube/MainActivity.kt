package com.frank.cube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frank.cube.ui.theme.CubeTheme

var steps by mutableStateOf("")
lateinit var mainActivity: MainActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = this
        setContent {
            CubeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            compute()
                        },
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting(steps)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CubeTheme {
        Greeting("Android")
    }
}

const val WHITE = 0
const val YELLOW = 1
const val BLUE = 2
const val ORANGE = 3
const val GREEN = 4
const val RED = 5

//1:  U
//-1: U'
//2:  U2
//3:  M2
val direction = intArrayOf(1, -1, 2, 3)
var face0 = arrayOf(
    intArrayOf(YELLOW, WHITE, YELLOW),
    intArrayOf(WHITE, YELLOW, WHITE),
    intArrayOf(YELLOW, WHITE, YELLOW)
)

var face1 = arrayOf(
    intArrayOf(WHITE, YELLOW, WHITE),
    intArrayOf(YELLOW, WHITE, YELLOW),
    intArrayOf(WHITE, YELLOW, WHITE)
)
var face2 = arrayOf(intArrayOf(RED, BLUE, RED), intArrayOf(BLUE, RED, BLUE))
var face3 = arrayOf(intArrayOf(GREEN, RED, GREEN), intArrayOf(RED, GREEN, RED))
var face4 = arrayOf(intArrayOf(ORANGE, GREEN, ORANGE), intArrayOf(GREEN, ORANGE, GREEN))
var face5 = arrayOf(intArrayOf(BLUE, ORANGE, BLUE), intArrayOf(ORANGE, BLUE, ORANGE))
var cube = arrayOf(face0, face1, face2, face3, face4, face5)
val queue = ArrayDeque(listOf(1))

var minStr =
    "asdfasdfasdfasdfsdddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd"

class MyThread : Thread() {
    override fun run() {
        super.run()
        for (i in 0 until 1000000) {
            if (compute0()) {
                if (steps.length < minStr.length) {
                    minStr = steps
                }
            }
        }
        steps = minStr
    }
}

fun compute() {
    steps = "..........."
    MyThread().start()
}

fun compute0(): Boolean {
    queue.clear()
    steps = ""

    face0 = arrayOf(
        intArrayOf(YELLOW, WHITE, YELLOW),
        intArrayOf(WHITE, YELLOW, WHITE),
        intArrayOf(YELLOW, WHITE, YELLOW)
    )
    face1 = arrayOf(
        intArrayOf(WHITE, YELLOW, WHITE),
        intArrayOf(YELLOW, WHITE, YELLOW),
        intArrayOf(WHITE, YELLOW, WHITE)
    )
    face2 = arrayOf(intArrayOf(RED, BLUE, RED), intArrayOf(BLUE, RED, BLUE))
    face3 = arrayOf(intArrayOf(GREEN, RED, GREEN), intArrayOf(RED, GREEN, RED))
    face4 = arrayOf(intArrayOf(ORANGE, GREEN, ORANGE), intArrayOf(GREEN, ORANGE, GREEN))
    face5 = arrayOf(intArrayOf(BLUE, ORANGE, BLUE), intArrayOf(ORANGE, BLUE, ORANGE))
    cube = arrayOf(face0, face1, face2, face3, face4, face5)

    for (i in 0 until 10) {
        var d = (Math.random() * 2).toInt()
        queue.add(d + 3)
        getNewStatus(d + 3)
        if (checkOK()) {
            while (!queue.isEmpty()) {
                steps += queue.last()
                queue.removeLast()
            }
            return true
        }
        d = (Math.random() * 3).toInt()
        val thisD = direction[d]
        getNewStatus(thisD)
        queue.add(thisD)
        if (checkOK()) {
            while (!queue.isEmpty()) {
                steps += queue.last()
                queue.removeLast()
            }
            return true
        }
    }
    return false
}

fun getNewStatus(d: Int) {
    val c20 = face0[2][0]
    val c10 = face0[1][0]
    val c00 = face0[0][0]
    val c21 = face0[2][1]
    val c11 = face0[1][1]
    val c01 = face0[0][1]
    val c22 = face0[2][2]
    val c12 = face0[1][2]
    val c02 = face0[0][2]

    val d1 = face2[0][0]
    val d2 = face2[0][1]
    val d3 = face2[0][2]
    val d4 = face3[0][0]
    val d5 = face3[0][1]
    val d6 = face3[0][2]
    val d7 = face4[0][0]
    val d8 = face4[0][1]
    val d9 = face4[0][2]
    val d10 = face5[0][0]
    val d11 = face5[0][1]
    val d12 = face5[0][2]

    when (d) {
        1 -> {
            face0[0][0] = c20
            face0[0][1] = c10
            face0[0][2] = c00
            face0[1][0] = c21
            face0[1][1] = c11
            face0[1][2] = c01
            face0[2][0] = c22
            face0[2][1] = c12
            face0[2][2] = c02
            face2[0][0] = d4
            face2[0][1] = d5
            face2[0][2] = d6
            face3[0][0] = d7
            face3[0][1] = d8
            face3[0][2] = d9
            face4[0][0] = d10
            face4[0][1] = d11
            face4[0][2] = d12
            face5[0][0] = d1
            face5[0][1] = d2
            face5[0][2] = d3
        }

        -1 -> {
            face0[0][0] = c02
            face0[0][1] = c12
            face0[0][2] = c22
            face0[1][0] = c01
            face0[1][1] = c11
            face0[1][2] = c21
            face0[2][0] = c00
            face0[2][1] = c10
            face0[2][2] = c20
            face2[0][0] = d10
            face2[0][1] = d11
            face2[0][2] = d12
            face3[0][0] = d1
            face3[0][1] = d2
            face3[0][2] = d3
            face4[0][0] = d4
            face4[0][1] = d5
            face4[0][2] = d6
            face5[0][0] = d7
            face5[0][1] = d8
            face5[0][2] = d9
        }

        2 -> {
            face0[0][0] = c22
            face0[0][1] = c21
            face0[0][2] = c20
            face0[1][0] = c12
            face0[1][1] = c11
            face0[1][2] = c10
            face0[2][0] = c02
            face0[2][1] = c01
            face0[2][2] = c00
            face2[0][0] = d7
            face2[0][1] = d8
            face2[0][2] = d9
            face3[0][0] = d10
            face3[0][1] = d11
            face3[0][2] = d12
            face4[0][0] = d1
            face4[0][1] = d2
            face4[0][2] = d3
            face5[0][0] = d4
            face5[0][1] = d5
            face5[0][2] = d6
        }

        3 -> {
            val f1 = face2[1][1]
            val f2 = face1[0][1]
            val f3 = face1[1][1]
            val f4 = face1[2][1]
            val f5 = face4[1][1]

            face0[0][1] = f2
            face0[1][1] = f3
            face0[2][1] = f4
            face1[0][1] = c01
            face1[1][1] = c11
            face1[2][1] = c21
            face2[0][1] = f5
            face2[1][1] = d8
            face4[0][1] = f1
            face4[1][1] = d2
        }

        4 -> {
            val f1 = face3[1][1]
            val f2 = face1[1][0]
            val f3 = face1[1][1]
            val f4 = face1[1][2]
            val f5 = face5[1][1]

            face0[1][0] = f4
            face0[1][1] = f3
            face0[1][2] = f2
            face1[1][0] = c12
            face1[1][1] = c11
            face1[1][2] = c10
            face3[0][1] = f5
            face3[1][1] = d11
            face5[0][1] = f1
            face5[1][1] = d5
        }
    }
}

fun checkOK(): Boolean {
    for (i in cube.indices) {
        val c = cube[i]
        val s = c.size
        val color = c[0][0]
        for (j in 0 until s) {
            val a = c[j]
            val ss = a.size
            for (k in 0 until ss) {
                if (a[k] != color) {
                    return false
                }
            }
        }
    }
    return true
}

// 14242324
// -->
// 1323 2423