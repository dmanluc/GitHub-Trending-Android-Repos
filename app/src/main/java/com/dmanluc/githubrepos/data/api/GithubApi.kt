package com.dmanluc.githubrepos.data.api

import com.dmanluc.githubrepos.data.contract.GithubSearchReposOutputContract
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
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

}