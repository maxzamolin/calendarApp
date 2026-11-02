package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.calendar.ui.CalendarScreen
import com.example.calendar.ui.theme.CalendarTheme
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFf8f6ff),
                                    Color(0xFFedf2ff),
                                    Color(0xFFf9f5ff)
                                )
                            )
                        )
                ) {
                    CalendarScreen()
                }
            }
        }
    }
}