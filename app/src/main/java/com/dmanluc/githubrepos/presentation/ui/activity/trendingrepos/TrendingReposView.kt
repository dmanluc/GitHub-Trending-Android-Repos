package com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseView

/**
 * View contract of TrendingReposActivity
 *
 * @author Daniel Manrique Lucas
 */
interface TrendingReposView : BaseView {

    fun showGithubRepoList()

    fun showGithubRepoDetails(githubRepo: GithubRepo)

    fun showConnectivityErrorDialog()

}
