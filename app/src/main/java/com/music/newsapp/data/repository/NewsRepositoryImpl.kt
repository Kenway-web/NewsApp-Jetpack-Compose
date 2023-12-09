package com.music.newsapp.data.repository

import android.provider.MediaStore
import com.music.newsapp.data.remote.NewsApi
import com.music.newsapp.domain.model.Article
import com.music.newsapp.domain.repository.NewsRepository
import com.music.newsapp.util.Resource

class NewsRepositoryImpl (
    private val newsApi: NewsApi
):NewsRepository{
        override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {
       return  try {
            val response = newsApi.getBreakingNews(category = category)
            Resource.Success(response.articles)
       }
       catch (e:Exception){
            Resource.Error(message = "Failed to fetch news ${e.message}")
       }
    }
}