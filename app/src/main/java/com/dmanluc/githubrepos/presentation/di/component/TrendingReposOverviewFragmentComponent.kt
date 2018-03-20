package com.dmanluc.githubrepos.presentation.di.component

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.mapper.GithubRepoContributorsMapper
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.data.repository.GithubRepositoryImpl
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidReposUseCase
import com.dmanluc.githubrepos.presentation.adapter.TrendingReposOverviewAdapter
import com.dmanluc.githubrepos.presentation.di.module.TrendingReposOverviewModule
import com.dmanluc.githubrepos.presentation.di.scope.FragmentScope
import com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list.TrendingReposOverviewFragment
import dagger.Component

/**
 * Component Interface for Dependency Injection (DI) in TrendingRepositoryOverviewFragment
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
@FragmentScope
@Component(modules = [(TrendingReposOverviewModule::class)], dependencies = [(AppComponent::class)])
interface TrendingReposOverviewFragmentComponent {

    fun inject(fragment: TrendingReposOverviewFragment)

    fun provideGithubApi(): GithubApi

    fun provideRepoApiMapper(): GithubReposMapper

    fun provideRepoContributorsApiMapper(): GithubRepoContributorsMapper

    fun provideGithubRepository(): GithubRepositoryImpl

    fun provideGithubTrendingAndroidReposUseCase(): GetGithubTrendingAndroidReposUseCase

    fun provideGithubTrendingAndroidReposAdapterListener(): TrendingReposOverviewAdapter.Listener

}