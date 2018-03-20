package com.dmanluc.githubrepos.presentation.di.component

import com.dmanluc.githubrepos.presentation.di.scope.ActivityScope
import com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos.TrendingReposActivity
import dagger.Component

/**
 * Component Interface for Dependency Injection (DI) in CharactersActivity
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
@ActivityScope
@Component(dependencies = [(AppComponent::class)])
interface TrendingReposActivityComponent {

    fun inject(activity: TrendingReposActivity)

}

