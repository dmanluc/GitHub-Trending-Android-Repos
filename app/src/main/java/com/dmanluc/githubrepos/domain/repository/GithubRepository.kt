package com.dmanluc.githubrepos.domain.repository

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import io.reactivex.Single

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
interface GithubRepository {

    fun getTrendingAndroidRepos(page: Int, itemsPerPage: Int) : Single<List<GithubRepo>>

}