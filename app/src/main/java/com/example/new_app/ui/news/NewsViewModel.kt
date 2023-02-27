package com.example.new_app.ui.news

import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.new_app.ApiManger
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.newsResponse.NewsResponse
import com.example.new_app.constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel :ViewModel() {
    var showLoading=MutableLiveData<Boolean>()
    var showErrorLayout=MutableLiveData<String>()
    var newsList= MutableLiveData<List<News?>?>()

     fun getNews(sourceId:String) {
       // showLoading()
        showLoading.value=true
        ApiManger.getApis()
            .getNews(constant.apiKay, sourceId)
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    if (response.isSuccessful) {
                        showLoading.value=false
                        newsList.value=response.body()?.articles
                        return
                    } else {
                        val errorMessage = Gson().fromJson(
                            response.errorBody()?.string(),
                            NewsResponse::class.java
                        )

                        showErrorLayout.value=errorMessage.message?:""

                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    showErrorLayout.value=t.localizedMessage
                }
            })
    }
}