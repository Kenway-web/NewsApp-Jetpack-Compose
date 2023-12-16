package com.music.newsapp.data.remote

import android.icu.lang.UCharacterCategory
import com.music.newsapp.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String="us",
        @Query("apiKey") apiKey: String = API_KEY,
    ) : NewsResponse

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY,
    ) : NewsResponse



    companion object{
        // GET https://newsapi.org/v2/top-headlines?country=us&apiKey=0c059dff

        const val BASE_URL="https://newsapi.org/v2/"
        const val API_KEY ="0c059dff69724402917f4c0c70a5a628"
    }

}