package com.example.new_app.repo.repositoriesImp.news

import com.example.new_app.repo.repositoriesContract.news.NewsRemoteDataSources
import com.example.new_app.repo.repositoriesContract.news.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsRepositoryModule {
    @Binds
    abstract fun getNewsRepo(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class NewsRemoteDataSourceModule {
    @Binds
    abstract fun getNewsRemoteData(newsRemoteDataSourcesImpl: NewsRemoteDataSourcesImpl): NewsRemoteDataSources
}