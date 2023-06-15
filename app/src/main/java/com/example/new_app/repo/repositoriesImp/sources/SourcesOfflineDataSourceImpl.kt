package com.example.new_app.repo.repositoriesImp.sources

import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.dataBase.NewsDatabase
import com.example.new_app.repo.repositoriesContract.sources.SourcesOfflineDataSource

class SourcesOfflineDataSourceImpl(var newsDatabase: NewsDatabase) : SourcesOfflineDataSource {
    override suspend fun getSourceByCategoryId(categoryId: String): List<Source?>? {

        try {
            var sourceResponse = newsDatabase.getNewsDao().getSources()
            return sourceResponse
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun saveSources(sources: List<Source>) {
        try {
            newsDatabase.getNewsDao().saveSources(source = sources)
        } catch (ex: Exception) {
            throw ex
        }
    }
}

