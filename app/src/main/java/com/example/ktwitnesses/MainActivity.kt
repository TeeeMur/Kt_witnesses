package com.example.ktwitnesses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.ktwitnesses.ui.MainScreen
import com.example.ktwitnesses.ui.theme.KtWitnessesTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			KtWitnessesTheme {
				MainScreen()
			}
		}
	}
}
