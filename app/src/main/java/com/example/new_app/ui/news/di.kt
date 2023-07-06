package com.example.new_app.ui.news

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class AdaptersModule {
    @Provides
    fun provideNewsAdapter(): NewsAdapter
    {
        return NewsAdapter(null)
    }
}