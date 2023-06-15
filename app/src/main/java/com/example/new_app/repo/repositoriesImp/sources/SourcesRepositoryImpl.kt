package com.example.new_app.repo.repositoriesImp.sources

import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.repo.NetworkHandler
import com.example.new_app.repo.repositoriesContract.sources.SourcesOfflineDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRemoteDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRepository

// TODO: هنا بنعتمد ع الانترفيس وليس الامبليمنيشن ف بناخد اوبجكت من الداتا كلاس
class SourcesRepositoryImpl(
    var remoteDataSource: SourcesRemoteDataSource,
    var offlineDataSource: SourcesOfflineDataSource,
    var networkHandler: NetworkHandler
) : SourcesRepository {
    override suspend fun getSourceByCategoryId(categoryId: String): List<Source?>? {
        if (networkHandler.isOnline()) {
            try {
                var data = remoteDataSource.getSourceByCategoryId(categoryId)
                offlineDataSource.saveSources(data as List<Source>)
                return data
            } catch (ex: Exception) {
                throw ex
            }

        } else {
            try {
                var data = offlineDataSource.getSourceByCategoryId(categoryId)
                offlineDataSource.saveSources(data as List<Source>)
                return data
            } catch (ex: Exception) {
                throw ex
            }

        }


    }

}