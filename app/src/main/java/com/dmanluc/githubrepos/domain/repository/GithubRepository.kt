package com.dmanluc.githubrepos.domain.repository

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import io.reactivex.Single

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    17/3/18.
 */
interface GithubRepository {

    fun getTrendingAndroidRepos(trendingOption: TrendingOption, page: Int, itemsPerPage: Int) : Single<List<GithubRepo>>

    enum class TrendingOption(val info: String) {
        TODAY("#today"),
        THIS_WEEK("#this week"),
        THIS_MONTH("this month"),
        THIS_YEAR("this_year")
    }

}