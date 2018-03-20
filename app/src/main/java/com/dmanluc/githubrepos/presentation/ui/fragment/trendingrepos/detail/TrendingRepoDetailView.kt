package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseView

/**
 * View contract of TrendingRepoDetailFragment
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
interface TrendingRepoDetailView: BaseView {

    fun showGithubRepoDetails(githubRepo: GithubRepo)

}