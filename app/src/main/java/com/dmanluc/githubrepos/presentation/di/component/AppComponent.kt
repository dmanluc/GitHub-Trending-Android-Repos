package com.dmanluc.githubrepos.presentation.di.component

import android.content.Context
import com.dmanluc.githubrepos.presentation.App
import com.dmanluc.githubrepos.presentation.di.module.AppModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * App Component Interface for Dependency Injection (DI)
 *
 * @author Daniel Manrique Lucas
 * @since  17 Mar 2018
 */
@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponent {

    fun inject(app: App)

    fun provideApplication(): Context

    fun provideRetrofit(): Retrofit

}