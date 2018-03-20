package com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos

import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseActivity
import com.dmanluc.githubrepos.presentation.di.component.DaggerTrendingReposActivityComponent
import com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail.TrendingRepoDetailFragment
import com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list.TrendingReposOverviewFragment
import javax.inject.Inject

/**
 * Main activity to handle navigation between trending android github repos list screen and selected repo´s detail screen
 *
 * @author Daniel Manrique Lucas
 */
class TrendingReposActivity : BaseActivity<TrendingReposView, TrendingReposPresenterImpl>(), TrendingReposView,
                              TrendingReposOverviewFragment.Callback {

    @Inject
    lateinit var internalPresenter: TrendingReposPresenterImpl

    override val layoutId
        get() = R.layout.base_activity_with_fragment
    override var screenTitle = ""
        get() = getString(R.string.app_name)
    override val presenter
        get() = internalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isOnline()) navigateToGithubRepoListScreen() else presenter.handleOnlineConnectivityError()
    }

    override fun showBackArrow(): Boolean = false

    override fun provideDaggerDependency() {
        DaggerTrendingReposActivityComponent.builder().appComponent(appComponent).build().inject(this)
    }

    override fun showLoadingProgress() {
        showLoading()
    }

    override fun hideLoadingProgress() {
        hideLoading()
    }

    override fun showGithubRepoList() {
        switchFragment(TrendingReposOverviewFragment.newInstance(), false)
    }

    override fun showGithubRepoDetails(githubRepo: GithubRepo) {
        switchFragment(TrendingRepoDetailFragment.newInstance(githubRepo), true)
    }

    override fun onGithubRepoSelected(githubRepo: GithubRepo) {
        presenter.goToGithubRepoDetailScreen(githubRepo)
    }

    override fun showConnectivityErrorDialog() {
        val alertBuilder = AlertDialog.Builder(this)

        alertBuilder.setTitle(getString(R.string.internet_connection_error_title))
        alertBuilder.setMessage(getString(R.string.internet_connection_error_text))
        alertBuilder.setCancelable(false)
        alertBuilder.setPositiveButton(getString(R.string.internet_connection_error_button_text).toUpperCase(),
                                       { _, _ -> System.exit(0) })

        alertBuilder.create().show()
    }

    private fun navigateToGithubRepoListScreen() {
        presenter.goToGithubRepoListScreen()
    }

}
