package com.dmanluc.githubrepos.presentation.di.component

import com.dmanluc.githubrepos.presentation.di.scope.FragmentScope
import com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail.TrendingRepoDetailFragment
import dagger.Component

/**
 * Component Interface for Dependency Injection (DI) in TrendingRepoDetailFragment
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    19/3/18.
 */
@FragmentScope
@Component(dependencies = [(AppComponent::class)])
interface TrendingRepoDetailFragmentComponent {

    fun inject(fragment: TrendingRepoDetailFragment)

}