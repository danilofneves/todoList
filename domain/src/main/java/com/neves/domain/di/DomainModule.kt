package com.neves.domain.di

import com.neves.domain.repository.TaskRepository
import com.neves.domain.usecases.FetchTask
import com.neves.domain.usecases.SaveTask
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

    @Provides
    fun provideFetchCards(repository: TaskRepository): FetchTask = FetchTask(repository)

    @Provides
    fun provideSaveCards(repository: TaskRepository): SaveTask = SaveTask(repository)
}