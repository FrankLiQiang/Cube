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
import androidx.compose.ui.unit.sp
import com.frank.cube.ui.theme.CubeTheme

var steps by mutableStateOf("")
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
//4:  HM2
val direction = intArrayOf(1, -1, 2, 3, 4)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CubeTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            compute()
                        }, color = MaterialTheme.colorScheme.background
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
        text = "Hello $name!", modifier = modifier,
        fontSize = 30.sp
    )
}

lateinit var face0: ArrayList<IntArray>
lateinit var face1: ArrayList<IntArray>
lateinit var face2: ArrayList<IntArray>
lateinit var face3: ArrayList<IntArray>
lateinit var face4: ArrayList<IntArray>
lateinit var face5: ArrayList<IntArray>
lateinit var cube: Array<ArrayList<IntArray>>
val queue = ArrayDeque(listOf(1))
var minStr = "S".repeat(50)

class MyThread : Thread() {
    override fun run() {
        super.run()
        for (i in 0 until 10000) {
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

// 14242324 --> (13)23(24)23    13 24 24 24
//    face0 = arrayListOf(
//        intArrayOf(YELLOW, WHITE, YELLOW),
//        intArrayOf(WHITE, YELLOW, WHITE),
//        intArrayOf(YELLOW, WHITE, YELLOW)
//    )
//    face1 = arrayListOf(
//        intArrayOf(WHITE, YELLOW, WHITE),
//        intArrayOf(YELLOW, WHITE, YELLOW),
//        intArrayOf(WHITE, YELLOW, WHITE)
//    )
//    face2 = arrayListOf(intArrayOf(RED, BLUE, RED), intArrayOf(BLUE, RED, BLUE))
//    face3 = arrayListOf(intArrayOf(GREEN, RED, GREEN), intArrayOf(RED, GREEN, RED))
//    face4 = arrayListOf(intArrayOf(ORANGE, GREEN, ORANGE), intArrayOf(GREEN, ORANGE, GREEN))
//    face5 = arrayListOf(intArrayOf(BLUE, ORANGE, BLUE), intArrayOf(ORANGE, BLUE, ORANGE))
//    cube = arrayOf(face0, face1, face2, face3, face4, face5)


// 3132 413
    face0 = arrayListOf(
        intArrayOf(YELLOW, YELLOW, YELLOW),
        intArrayOf(YELLOW, YELLOW, YELLOW),
        intArrayOf(YELLOW, YELLOW, YELLOW)
    )
    face1 = arrayListOf(
        intArrayOf(WHITE, WHITE, WHITE),
        intArrayOf(WHITE, WHITE, WHITE),
        intArrayOf(WHITE, WHITE, WHITE)
    )
    face2 = arrayListOf(intArrayOf(RED, BLUE, RED), intArrayOf(RED, BLUE, RED))
    face3 = arrayListOf(intArrayOf(GREEN, ORANGE, GREEN), intArrayOf(GREEN, ORANGE, GREEN))
    face4 = arrayListOf(intArrayOf(ORANGE, GREEN, ORANGE), intArrayOf(ORANGE, GREEN, ORANGE))
    face5 = arrayListOf(intArrayOf(BLUE, RED, BLUE), intArrayOf(BLUE, RED, BLUE))
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
