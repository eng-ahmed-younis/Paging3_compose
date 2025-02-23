package com.images.di

import com.images.data.data_sorce.remote.service.NewsServiceApi
import com.images.data.repository.NewsRepositoryImpl
import com.images.domain.NewsRepository
import com.images.use_case.GetArticlesUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class )
object UseCaseModule {


    // Provides the GetArticlesUseCase
    @Singleton
    @Provides
    fun provideNewsUseCase(repository: NewsRepository): GetArticlesUseCase {
        return GetArticlesUseCase(repository)
    }

}