package com.dmanluc.githubrepos.domain.interactor

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import io.reactivex.Single

/**
 * Use case to get trending android repos from Github API (with pagination)
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
/**
 *
 * @author Daniel Manrique Lucas
 */
class GetGithubTrendingAndroidRepos(private val repository: GithubRepository) :
        UseCase<List<GithubRepo>, Pair<GithubRepository.TrendingOption, Pair<Int, Int>>>() {

    override fun buildUseCaseObservable(
            params: Pair<GithubRepository.TrendingOption, Pair<Int, Int>>): Single<List<GithubRepo>> = repository.getTrendingAndroidRepos(
            params.first,
            params.second.first,
            params.second.second)

}