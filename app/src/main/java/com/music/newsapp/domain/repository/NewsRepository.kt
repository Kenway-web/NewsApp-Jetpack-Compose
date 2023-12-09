package com.music.newsapp.domain.repository

import android.provider.MediaStore.Audio.Artists
import com.music.newsapp.domain.model.Article
import com.music.newsapp.util.Resource

interface NewsRepository {

    suspend fun getTopHeadlines(
        category:String
    ):Resource<List<Article>>
}