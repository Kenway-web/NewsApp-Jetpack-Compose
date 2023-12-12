package com.music.newsapp.presentation.article_screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    url:String,
    onBackPressed:()-> Unit
){
        val context= LocalContext.current
        var isLoading by remember { mutableStateOf(true) }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text(text = "Article", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBackPressed) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Search")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors (
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        )
        {  padding ->
                Box(
                   modifier = Modifier
                       .fillMaxSize()
                       .padding(padding),
                    contentAlignment = Alignment.Center
                ) {


                    //webview
                    AndroidView(factory = {
                        WebView(context).apply {
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isLoading = false
                                }
                            }
                            loadUrl(url?:"")
                        }
                    })
                    if(isLoading&&url!=null)
                    {
                         CircularProgressIndicator()
                    }

                }
        }

}