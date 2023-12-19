package com.pdm.thirdappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.pdm.thirdappcompose.ui.theme.ThirdAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThirdAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var state by rememberSaveable { mutableStateOf(true) }
                    Main(state) { state = !state }
                }
            }
        }
    }
}

@Composable
fun Main(state: Boolean, checkedChange: (Boolean) -> Unit) {
    var imgIndex by rememberSaveable { mutableStateOf(0) }
    val listOfImgs = listOf(R.drawable.img1, R.drawable.img2, R.drawable.img3)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val startGuideline = createGuidelineFromStart(0.1f)
        val endGuideline = createGuidelineFromEnd(0.1f)
        val topGuideline = createGuidelineFromTop(0.15f)
        val bottomGuideline = createGuidelineFromBottom(0.15f)
        val (rowID, bottomRowID, paragraphID, textID) = createRefs()
        val barrier = createBottomBarrier(rowID, paragraphID)
        var textContent = "Check pulsado"
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(rowID) {
                    top.linkTo(topGuideline)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Switch(checked = state, onCheckedChange = checkedChange)
            Spacer(modifier = Modifier.width(10.dp))
            if (state)
                Text(text = "Hide")
            else
                Text(text = "Show")
        }
        textContent = if (state) {
            Text(modifier = Modifier
                .constrainAs(paragraphID) {
                    top.linkTo(rowID.bottom)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                },
                text = "PARRAFACO SUPER LARGO  QUE EN VERDAD DEBERÍA SER MÁS LARGO PERO NO SE ME OCURREN MÁS COSAS QUE ESCRIBIR"
            )
            "Check pulsado"
        } else {
            "Check NO pulsado"
        }
        Text(text = textContent,
            modifier = Modifier.constrainAs(textID) {
                top.linkTo(barrier)
                start.linkTo(startGuideline)
                end.linkTo(endGuideline)
            })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(bottomRowID) {
                    bottom.linkTo(bottomGuideline)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = { imgIndex = queuedList(imgIndex, listOfImgs.size, false) }) {
                Text("Prev")
            }
            Image(
                modifier = Modifier.size(100.dp),
                contentDescription = "Image",
                painter = painterResource(id = listOfImgs[imgIndex])
            )
            Button(onClick = { imgIndex = queuedList(imgIndex, listOfImgs.size, true) }) {
                Text("Next")
            }
        }
    }
}

fun queuedList(imgIndex: Int, size: Int, increase: Boolean) =
    if (increase) {
        if (imgIndex >= size - 1) 0
        else imgIndex + 1
    } else {
        if (imgIndex == 0) size - 1
        else imgIndex - 1
    }

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ThirdAppComposeTheme {
        var state by rememberSaveable { mutableStateOf(true) }
        Main(state) { state = !state }
    }
}