package com.dmanluc.githubrepos.data.repository

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Github repository implementation
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
class GithubRepositoryImpl
@Inject constructor(private val api: GithubApi, private val mapper: GithubReposMapper) : GithubRepository {

    override fun getTrendingAndroidRepos(page: Int, itemsPerPage: Int): Single<List<GithubRepo>> {
        val query = "android"
        val sortType = "stars"
        val orderType = "desc"

        return api.fetchTrendingAndroidRepos(query,
                                             sortType,
                                             orderType,
                                             page,
                                             itemsPerPage).map { mapper.mapApiContractToDomainModel(it) }
    }

}