package com.yjq.cp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.yjq.cp.ui.theme.CpTheme
import com.yjq.cp.composeable.MainCompose

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CpTheme {
                MainCompose {
                    finish()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CpTheme {
        Greeting("Android")
    }
}