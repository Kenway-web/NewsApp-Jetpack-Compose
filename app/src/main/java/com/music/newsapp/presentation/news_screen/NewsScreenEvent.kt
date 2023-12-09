package com.music.newsapp.presentation.news_screen

import com.music.newsapp.domain.model.Article

sealed class NewsScreenEvent{
    data class OnNewsCardClicked(val article:Article):NewsScreenEvent()
    data class OnCategoryChanged(val category:String):NewsScreenEvent()
    data class OnSearchQueryChanged(val searchQuery: String):NewsScreenEvent()
    object OnSearchIconClicked:NewsScreenEvent()
    object OnClosedIconClicked:NewsScreenEvent()
}
