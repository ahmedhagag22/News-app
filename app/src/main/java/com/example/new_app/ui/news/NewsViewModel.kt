package com.example.new_app.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_app.ApiManger
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.newsResponse.NewsResponse
import com.example.new_app.constant
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    var showLoading = MutableLiveData<Boolean>()
    var showErrorLayout = MutableLiveData<String>()
    var newsList = MutableLiveData<List<News?>?>()

    // call api >> by the coroutine

    fun getNews(sourceId: String, pageSize: Int, page: Int) {
        // showLoading()
        showLoading.value = true
        viewModelScope.launch {
            try {
                var DataResponse = ApiManger.getApis()
                    .getNews(constant.apiKay, sourceId, pageSize, page)
                showLoading.value = false
                newsList.value = DataResponse.articles

            } catch (t: HttpException) {
                val errorMessage = Gson().fromJson(
                    t.response()?.errorBody()?.string(),
                    NewsResponse::class.java
                )
            }
            //failure
            catch (e: Exception) {
                showErrorLayout.value = e.localizedMessage
            }
        }


        // TODO: traditional way >> call back without kotlin coroutines
//        ApiManger.getApis()
//            .getNews(constant.apiKay, sourceId, pageSize, page)
//            .enqueue(object : Callback<NewsResponse> {
//                override fun onResponse(
//                    call: Call<NewsResponse>,
//                    response: Response<NewsResponse>
//                ) {
//                    if (response.isSuccessful) {
//                        showLoading.value = false
//                        newsList.value = response.body()?.articles
//                        return
//                    } else {
//                        val errorMessage = Gson().fromJson(
//                            response.errorBody()?.string(),
//                            NewsResponse::class.java
//                        )
//
//                        showErrorLayout.value = errorMessage.message ?: ""
//
//                    }
//                }
//
//                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
//                    showErrorLayout.value = t.localizedMessage
//                }
//            })
    }

}