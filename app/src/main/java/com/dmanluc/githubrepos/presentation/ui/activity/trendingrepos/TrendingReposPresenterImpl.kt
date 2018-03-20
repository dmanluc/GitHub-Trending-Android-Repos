package com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BasePresenter
import org.parceler.Parcel
import javax.inject.Inject

/**
 * Presenter of TrendingReposActivity
 *
 * @author Daniel Manrique Lucas
 */
class TrendingReposPresenterImpl @Inject constructor() : BasePresenter<TrendingReposView, TrendingReposPresenterImpl.State>() {

    fun goToGithubRepoListScreen() {
        if (isViewAttached()) view?.showGithubRepoList()
    }

    fun goToGithubRepoDetailScreen(githubRepo: GithubRepo) {
        if (isViewAttached()) view?.showGithubRepoDetails(githubRepo)
    }

    fun handleOnlineConnectivityError() {
        if (isViewAttached()) view?.showConnectivityErrorDialog()
    }

    override fun newState(): State = State()

    @Parcel
    class State : BasePresenter.State
}
