package com.example.new_app.repo.repositoriesImp.sources

import com.example.new_app.repo.repositoriesContract.sources.SourcesRemoteDataSource
import com.example.new_app.repo.repositoriesContract.sources.SourcesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SourcesRepModule {
    @Binds
    abstract fun getSourcesRepo(sourcesRepositoryImpl: SourcesRepositoryImpl): SourcesRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class SourcesRemoteDataModule {
    @Binds
    abstract fun getSourcesRemote(sourcesRemoteDataSourceImpl: SourcesRemoteDataSourceImpl): SourcesRemoteDataSource
}