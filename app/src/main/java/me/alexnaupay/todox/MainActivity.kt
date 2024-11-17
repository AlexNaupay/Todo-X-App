package me.alexnaupay.todox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import me.alexnaupay.todox.presentation.screens.home.HomeScreenRoot
import me.alexnaupay.todox.ui.theme.TodoXAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoXAppTheme {
                HomeScreenRoot()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoXAppTheme {
        HomeScreenRoot()
    }
}