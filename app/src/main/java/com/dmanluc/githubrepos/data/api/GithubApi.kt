package com.dmanluc.githubrepos.data.api

import com.dmanluc.githubrepos.data.contract.GithubRepoContributorOutputContract
import com.dmanluc.githubrepos.data.contract.GithubSearchReposOutputContract
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * GitHub API for searching repository data
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
interface GithubApi {

    @GET("search/repositories")
    fun fetchTrendingAndroidRepos(
            @Query("q") query: String,
            @Query("sort") sortType: String,
            @Query("order") orderType: String,
            @Query("page") currentPage: Int,
            @Query("per_page") itemsPerPage: Int): Single<GithubSearchReposOutputContract>

    @GET
    fun fetchRepoContributors(@Url repoUrl: String): Single<List<GithubRepoContributorOutputContract>>

}