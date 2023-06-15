package com.example.new_app.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.new_app.ApiManger
import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.model.newsResponse.NewsResponse
import com.example.new_app.dataBase.NewsDatabase
import com.example.new_app.repo.NetworkHandler
import com.example.new_app.repo.repositoriesContract.news.NewsRepository
import com.example.new_app.repo.repositoriesContract.sources.SourcesOfflineDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRemoteDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRepository
import com.example.new_app.repo.repositoriesImp.news.NewsRemoteDataSourcesImpl
import com.example.new_app.repo.repositoriesImp.news.NewsRepositoryImpl
import com.example.new_app.repo.repositoriesImp.sources.SourcesOfflineDataSourceImpl
import com.example.new_app.repo.repositoriesImp.sources.SourcesRemoteDataSourceImpl
import com.example.new_app.repo.repositoriesImp.sources.SourcesRepositoryImpl
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NewsViewModel : ViewModel() {
    var showLoading = MutableLiveData<Boolean>()
    var showErrorLayout = MutableLiveData<String>()
    var newsList = MutableLiveData<List<News?>?>()
    var networkHandler: NetworkHandler
    var sourcesOffLineDataSource: SourcesOfflineDataSource
    var sourcesRemoteDataSource: SourcesRemoteDataSource
    var sourcesRepository: SourcesRepository


    init {
        networkHandler = object : NetworkHandler {
            override fun isOnline(): Boolean {
                return true
            }
        }
        sourcesRemoteDataSource = SourcesRemoteDataSourceImpl(ApiManger.getApis())
        sourcesOffLineDataSource = SourcesOfflineDataSourceImpl(NewsDatabase.getInstance()!!)
        sourcesRepository = SourcesRepositoryImpl(
            remoteDataSource = sourcesRemoteDataSource,
            offlineDataSource = sourcesOffLineDataSource,
            networkHandler = networkHandler
        )

    }

    // manual dependency injection
    var newsRemoteDataSources = NewsRemoteDataSourcesImpl(ApiManger.getApis())

    //obj of newsRepo
    var newsRepository: NewsRepository = NewsRepositoryImpl(newsRemoteDataSources)

    // call api >> by the coroutine

    fun getNews(sourceId: String, pageSize: Int, page: Int) {
        // showLoading()
        showLoading.value = true
        viewModelScope.launch {
            try {
                var newsResponse = newsRepository
                showLoading.value = false
                newsList.value = newsResponse.getNewsBySourceId(sourceId, pageSize, page)

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