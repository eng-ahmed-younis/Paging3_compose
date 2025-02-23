package com.images.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)  // Install in the SingletonComponent for app-wide scope
object DispatcherModule {

    @Provides
    @Singleton
    @Named(IO_DISPATCHER)
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    @Named(MAIN_DISPATCHER)
    fun provideMainDispatcher() = Dispatchers.Main

    @Provides
    @Singleton
    @Named(DEFAULT_DISPATCHER)
    fun provideDefaultDispatcher() = Dispatchers.Default
}


const val IO_DISPATCHER = "ioDispatcher"
const val MAIN_DISPATCHER = "mainDispatcher"
const val DEFAULT_DISPATCHER = "defaultDispatcher"
