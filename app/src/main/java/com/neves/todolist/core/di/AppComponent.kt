package com.neves.todolist.core.di

import android.app.Application
import com.neves.data.di.DataModule
import com.neves.domain.di.DomainModule
import com.neves.todolist.core.App
import com.neves.todolist.presentation.viewModels.TaskViewModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule


import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        MainActivityModuleBuilder::class,
        ContextModule::class,
        DataModule::class,
        DomainModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: App)
}