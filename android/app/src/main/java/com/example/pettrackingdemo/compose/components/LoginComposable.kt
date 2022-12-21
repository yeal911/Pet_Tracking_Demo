package com.example.pettrackingdemo.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class LoginComposable {
    lateinit var textHolder: MutableState<String>

    @Composable
    fun Render(initValue: String = "") {
        textHolder = remember { mutableStateOf(initValue) }

        Surface(
            Modifier
                .fillMaxWidth()
                .height(Dp(100f))
        ) {
            Box(

                modifier = Modifier
                    .background(color = Color.LightGray)

                    .border(
                        shape = RoundedCornerShape(5.dp),
                        width = 1.dp,
                        color = Color.Black
                    )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = textHolder.value,
                        fontSize = 40.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Render()
    }
}