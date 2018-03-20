package com.dmanluc.githubrepos.presentation.di.module

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.mapper.GithubRepoContributorsMapper
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.data.repository.GithubRepositoryImpl
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidReposUseCase
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
 * @author   Daniel Manrique <dmanluc91@gmail.com>
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
    fun provideRepoApiMapper(): GithubReposMapper = GithubReposMapper()

    @FragmentScope
    @Provides
    fun provideRepoContributorsApiMapper(): GithubRepoContributorsMapper = GithubRepoContributorsMapper()

    @FragmentScope
    @Provides
    fun provideGithubRepository(service: GithubApi,
                                reposMapper: GithubReposMapper,
                                contributorsMapper: GithubRepoContributorsMapper): GithubRepository = GithubRepositoryImpl(service, reposMapper, contributorsMapper)

    @FragmentScope
    @Provides
    fun provideGithubTrendingAndroidReposUseCase(repository: GithubRepository) = GetGithubTrendingAndroidReposUseCase(repository)

    @FragmentScope
    @Provides
    fun provideGithubTrendingAndroidReposAdapterListener(): TrendingReposOverviewAdapter.Listener {
        return fragment
    }

}