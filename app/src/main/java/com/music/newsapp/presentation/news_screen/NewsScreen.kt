package com.music.newsapp.presentation.news_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.music.newsapp.domain.model.Article
import com.music.newsapp.presentation.component.BottomSheetComponent
import com.music.newsapp.presentation.component.CategoryTabRow
import com.music.newsapp.presentation.component.NewsArticleCard
import com.music.newsapp.presentation.component.NewsScreenTopBar
import com.music.newsapp.presentation.component.RetryContent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NewsScreen(
    state: NewsScreenState,
    onEvent:(NewsScreenEvent) -> Unit,
    onReadFullStoryButtonClicked:(String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val categories = listOf<String>(
        "General", "Business", "Health", "Science", "Sports", "Technology", "Entertainment"
    )

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var shouldBottomSheetShow by remember { mutableStateOf(false) }


    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.selectedArticle?.let {
                    BottomSheetComponent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClicked(it.url)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) shouldBottomSheetShow = false
                            }
                        }
                    )
                }
            }
        )
    }

    LaunchedEffect(key1 = pagerState)
    {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(NewsScreenEvent.OnCategoryChanged(category = categories[page]))
        }
    }
    // snapshotFlow { pagerState.currentPage } creates a Flow of the current page snapshot.
    //This means that the flow will only emit a new value whenever the current page changes.


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            NewsScreenTopBar(
                scrollBehavior = scrollBehavior,
                onSearchIconClicked = {}
            )
        },
    )
    { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            CategoryTabRow(
                pagerState = pagerState,
                categories = categories,
                onTabSelected = { index ->
                    coroutineScope.launch { pagerState.animateScrollToPage(index) }
                })

            HorizontalPager(
                pageCount = categories.size,
                state = pagerState
            ) {
                 NewsArticleList(
                     state = state,
                     onCardClicked = { article->
                         shouldBottomSheetShow=true
                         onEvent(NewsScreenEvent.OnNewsCardClicked(article=article))
                     },
                     onRetry = {
                         onEvent(NewsScreenEvent.OnCategoryChanged(state.category))
                     }
                 )
            }
        }
    }

}
    @Composable
    fun NewsArticleList(
        state: NewsScreenState,
        onCardClicked : (Article) -> Unit,
        onRetry:()->Unit,
    ){
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(state.articles) { article ->
                NewsArticleCard(
                    article = article,
                    onCardClicked = onCardClicked
                )
            }
        }

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
                if(state.isLoading)
                {
                    CircularProgressIndicator()
                }
                if(state.error!=null)
                {
                    RetryContent(
                        error = state.error,
                        onRetry = onRetry
                    )
                }
        }
    }



// state : Any value that can change during the usage of the app
// event : All possible actions user can do