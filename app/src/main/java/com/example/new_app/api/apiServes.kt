package com.example.new_app.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface apiServes {
    @GET("v2/top-headlines/sources")
    //bass the apiKey in parmater func
    fun getSources(@Query ("apiKey")apiKy:String):Call<SourcesResponse>
}