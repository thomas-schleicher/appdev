package com.example.ex4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBars
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import com.example.ex4.ui.theme.Ex4Theme
import androidx.compose.ui.res.imageResource
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.input.pointer.pointerInput

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ex4Theme {
                TransformScreen()
            }
        }
    }
}

@Composable
fun TransformScreen() {
    val imageBitmap = ImageBitmap.imageResource(id = R.drawable.catpicture)
    val defaultScale = 1f
    val defaultAngle = 0f
    var defaultOffset = Offset.Zero
    var scale by remember { mutableFloatStateOf(defaultScale) }
    var angle by remember { mutableFloatStateOf(defaultAngle) }
    var offset by remember { mutableStateOf(defaultOffset) }

    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        angle += rotationChange
        offset += offsetChange
    }
    Scaffold(
        // top Padding to be able to read values without overlapping
        modifier = Modifier.fillMaxSize().padding(top = with(LocalDensity.current){WindowInsets.statusBars.getTop(LocalDensity.current).toDp()}),
        topBar = {
            Column {
                Text(text = "Zoom: ${scale}x")
                Text(text = "Rotation angle: ${angle}°")
                Text(text = "Offset: ${offset.x}p; ${offset.y}p)")
            }
        }
    )
    { innerPadding ->
        Canvas(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .transformable(state = state)
                .pointerInput(Unit){
                    detectTapGestures(
                        onDoubleTap = {
                            scale = defaultScale
                            angle = defaultAngle
                            offset = Offset.Zero
                        }
                    )
                }
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    rotationZ = angle
                    translationX = offset.x
                    translationY = offset.y
                }
        ) {
            defaultOffset = Offset(center.x-imageBitmap.width/2, center.y-imageBitmap.height/2)
            drawImage(
                image = imageBitmap,
                topLeft = Offset(center.x-imageBitmap.width/2, center.y-imageBitmap.height/2) // Change this to position the image
            )
        }
    }
}