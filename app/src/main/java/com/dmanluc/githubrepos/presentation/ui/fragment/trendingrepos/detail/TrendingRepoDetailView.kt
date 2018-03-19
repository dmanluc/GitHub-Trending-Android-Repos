package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseView

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
interface TrendingRepoDetailView: BaseView {

    fun showGithubRepoDetails(githubRepo: GithubRepo)

}