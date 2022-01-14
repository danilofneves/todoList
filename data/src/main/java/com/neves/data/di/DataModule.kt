package com.neves.data.di

import android.content.Context
import com.neves.data.local.LocalDataSource
import com.neves.data.local.LocalDataSourceImpl
import com.neves.data.local.database.AppDatabase
import com.neves.data.local.database.AppDatabase.Companion.getDatabase
import com.neves.data.local.database.TaskDAO
import com.neves.data.repository.TaskRepositoryImpl
import com.neves.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase = getDatabase(context)

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): TaskDAO = database.taskDao()

    @Provides
    fun provideLocalDataSource(dao: TaskDAO): LocalDataSource = LocalDataSourceImpl(dao)

    @Singleton
    @Provides
    fun bindNewsRepository(localDataSource: LocalDataSource): TaskRepository = TaskRepositoryImpl(localDataSource)

}