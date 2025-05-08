package com.example.ex3

import androidx.compose.foundation.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerId
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import com.example.ex3.ui.theme.Ex3Theme
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex3Theme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(){
    val count = remember { mutableIntStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Current Fingers: ${count.intValue}")
                }
            )
        }
    )
    { innerPadding ->
        MultiTouchTracking(
            modifier = Modifier.padding(innerPadding),
            updateCount = { newCount -> count.intValue = newCount }
        )
    }
}

@Composable
fun MultiTouchTracking(modifier: Modifier = Modifier, updateCount: (Int) -> Unit) {
    val textMeasurer = rememberTextMeasurer()
    val textColor = MaterialTheme.colorScheme.onSurface
    val fingers = remember { mutableStateMapOf<PointerId, SnapshotStateList<Offset>>()} // SnapshotStateList triggers recomposition of UI!
    val strokes = mutableMapOf<PointerId, List<Offset>>()

    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit){
            awaitPointerEventScope {
                while(true){
                    val event = awaitPointerEvent()
                    val changes = event.changes
                    for (change in changes) {
                        val id = change.id
                        val pos = change.position
                        val prev = change.previousPosition
                        val isDown = change.pressed
                        if(!isDown){ // just lifted => delete finger
                            strokes[id] = fingers[id] as List<Offset>
                            fingers.remove(id)//TODO: check if worked
                            //Log.d("TouchTracking", "Deleted: ${fingers[id]}")
                            continue
                        } //else:
                        if(!change.previousPressed){ // first contact => add finger to list
                            fingers[id] = mutableStateListOf(pos) //TODO: check if worked
                            //Log.d("TouchTracking", "Created: ${fingers[id]}")
                            continue
                        }
                        if(pos != prev){ // finger moved => add new position
                            fingers[id]?.add(pos) // TODO: check if is really there
                            //Log.d("TouchTracking", "Updated: ${fingers[id]}")
                        }
                        // else: finger did not move = no change
                        // change.consume() ????
                    }
                }
            }
        }
    ) {
        val circleRadius = size.minDimension/16
        val lineWidth = circleRadius/4

        updateCount(fingers.size)

        for((id, path) in fingers){
            //Log.d("TouchTracking", "Saved Touches: $id, $path")
            Log.d("TouchTracking", "ID: $id")

            val uniqueColor = getColor(id)
            drawCircle(color= uniqueColor, center = Offset(x=path.last().x, y=path.last().y), radius = circleRadius)
            drawID(id, Offset(x=path.last().x, y=path.last().y), textMeasurer, textColor)
            drawPath(path, uniqueColor, lineWidth)
        }

        for((id, path) in strokes){
            val uniqueColor = getColor(id)
            drawPath(path, uniqueColor, lineWidth)
            drawID(id, path, textMeasurer, textColor)
        }

        //drawCircle(color= Color.Green, center = Offset(x=width/2f, y=height/2f), radius = size.minDimension/2)

    }
}

val pointerColors = listOf(
    Color.Red, Color.Green, Color.Blue, Color.Magenta, Color.Cyan, Color.Yellow, Color.Gray,
    Color.Black, Color.White, Color.DarkGray, Color.LightGray
)
fun getColor(id: PointerId):Color{
    return pointerColors[id.value.toInt()%pointerColors.size]
    /*// completely unique colors => use hash-table:
    val hue = (abs(id.value.hashCode()) % 360).toFloat()
    Log.d("TouchTracking", "ID: ${id.value}, Hash: ${abs(id.value.hashCode())}, Hue: $hue")
    return Color.hsl(hue, 0.8f, 0.6f)*/
}

fun DrawScope.drawPath(path: List<Offset>, uniqueColor: Color, lineWidth: Float) {
    path.windowed(2).forEach { (from, to) ->
        drawLine(uniqueColor, from, to, lineWidth)
    }
}

fun DrawScope.drawID(id: PointerId, position: Offset, textMeasurer: TextMeasurer, textColor: Color){
    val style = TextStyle(
        fontSize = 40.sp,
        color = textColor
    )
    drawText(
        textMeasurer,
        text = id.value.toString(),
        style = style,
        topLeft = position
    )
}

fun DrawScope.drawID(id: PointerId, path: List<Offset>, textMeasurer: TextMeasurer, textColor: Color){
    val position = path[path.size/2]
    drawID(id, position, textMeasurer, textColor)
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Ex3Theme {
        MainScreen()
    }
}