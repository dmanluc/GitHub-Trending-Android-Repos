package com.dmanluc.githubrepos.presentation

import android.app.Application
import com.dmanluc.githubrepos.BuildConfig
import com.dmanluc.githubrepos.presentation.di.component.AppComponent
import com.dmanluc.githubrepos.presentation.di.component.DaggerAppComponent
import com.dmanluc.githubrepos.presentation.di.module.AppModule

/**
 * Application class
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17 Mar 2018
 */
class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this, BuildConfig.GITHUB_BASE_URL)).build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

}