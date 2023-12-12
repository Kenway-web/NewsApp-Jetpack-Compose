package com.music.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.music.newsapp.presentation.news_screen.NewsScreen
import com.music.newsapp.presentation.news_screen.NewsScreenViewModel
import com.music.newsapp.presentation.theme.NewsAppTheme
import com.music.newsapp.util.NavGraphSetup
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                val navController = rememberNavController()
                NavGraphSetup(navController = navController)
            }
        }
    }
}

