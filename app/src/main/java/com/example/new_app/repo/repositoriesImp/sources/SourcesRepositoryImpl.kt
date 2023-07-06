package com.example.new_app.repo.repositoriesImp.sources

import com.example.new_app.api.model.sourcesResponse.Source
import com.example.new_app.repo.repositoriesContract.sources.SourcesRemoteDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRepository
import javax.inject.Inject

// TODO: هنا بنعتمد ع الانترفيس وليس الامبليمنيشن ف بناخد اوبجكت من الداتا كلاس
class SourcesRepositoryImpl @Inject constructor(var remoteDataSource: SourcesRemoteDataSource):SourcesRepository {
    override suspend fun getSourceByCategoryId(categoryId: String): List<Source?>? {
        var data =remoteDataSource.getSourceByCategoryId(categoryId)
        return data
    }

}