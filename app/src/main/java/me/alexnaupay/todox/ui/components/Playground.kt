package me.alexnaupay.todox.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.alexnaupay.todox.ui.theme.TodoXAppTheme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun HelloWorldText(modifier: Modifier = Modifier) {
    Text(
        text = "Hello World", fontWeight = FontWeight.Bold, color = Color.Blue,
        modifier = modifier.background(Color.LightGray).padding(16.dp)
    )
}

@Composable
fun ClickableText(modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("Click me!") }
    Text(
        text = text, fontWeight = FontWeight.Bold, color = Color.Blue,
        modifier = modifier.background(Color.LightGray).padding(16.dp)
            .clickable{
                text = "Clicked!"
            }
    )
}

@Composable
@Preview(showBackground = true)
fun HelloWorldPreview() {
    TodoXAppTheme {
        HelloWorldText()
    }
}

@Composable
@Preview(showBackground = true)
fun ClickableTextPreview() {
    TodoXAppTheme {
        ClickableText()
    }
}