package com.example.new_app

import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.newsResponse.NewsResponse
import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.api.model.sourcesResponse.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface apiServes {
    @GET("v2/top-headlines/sources")
    //bass the apiKey in parmater func
    fun getSources(@Query ("apiKey")apiKy:String):Call<SourcesResponse>
    @GET("v2/everything")
    fun getNews(@Query ("apiKey")apiKy:String
    ,@Query("sources")sources:String):Call<NewsResponse>
}