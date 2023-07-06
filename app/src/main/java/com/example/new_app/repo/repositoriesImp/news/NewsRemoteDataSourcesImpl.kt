package com.example.new_app.repo.repositoriesImp.news

import com.example.new_app.api.model.newsResponse.News
import com.example.new_app.api.apiServes
import com.example.new_app.api.constant
import com.example.new_app.repo.repositoriesContract.news.NewsRemoteDataSources
import javax.inject.Inject

class NewsRemoteDataSourcesImpl @Inject constructor(var apiServes: apiServes) :NewsRemoteDataSources {
    override suspend fun getNewsBySourceId(sourceId: String, pageSize: Int, page: Int): List<News?>? {
var news=apiServes.getNews(constant.apiKay,sourceId,pageSize,page)
    return news.articles
    }

}