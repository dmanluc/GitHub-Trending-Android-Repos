package com.dmanluc.githubrepos.presentation.di.module

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.data.repository.GithubRepositoryImpl
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidRepos
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import com.dmanluc.githubrepos.presentation.adapter.TrendingReposOverviewAdapter
import com.dmanluc.githubrepos.presentation.di.scope.FragmentScope
import com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list.TrendingReposOverviewFragment
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

/**
 * Module class for providing dependencies
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
@Module
class TrendingReposOverviewModule(private val fragment: TrendingReposOverviewFragment) {

    @FragmentScope
    @Provides
    fun provideGithubService(retrofit: Retrofit): GithubApi {
        return retrofit.create<GithubApi>(GithubApi::class.java)
    }

    @FragmentScope
    @Provides
    fun provideApiMapper(): GithubReposMapper = GithubReposMapper()

    @FragmentScope
    @Provides
    fun provideGithubRepository(service: GithubApi, mapper: GithubReposMapper): GithubRepository = GithubRepositoryImpl(service, mapper)

    @FragmentScope
    @Provides
    fun provideGithubTrendingAndroidReposUseCase(repository: GithubRepository) = GetGithubTrendingAndroidRepos(repository)

    @FragmentScope
    @Provides
    fun provideGithubTrendingAndroidReposAdapterListener(): TrendingReposOverviewAdapter.Listener {
        return fragment
    }

}