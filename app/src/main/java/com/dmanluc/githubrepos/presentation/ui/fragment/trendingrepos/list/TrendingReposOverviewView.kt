package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseView

/**
 * View contract of TrendingReposOverviewFragment
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
interface TrendingReposOverviewView : BaseView {

    fun showGithubRepoList(githubRepos: List<GithubRepo>, moreLoadingMode: Boolean)

    fun showLoadingProgress()

    fun hideLoadingProgress()

    fun handleFloatingMenu(enable: Boolean)

    fun showGithubApiErrorMessage(errorMessage: String?)

}