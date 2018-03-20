package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BasePresenter
import org.parceler.Parcel
import javax.inject.Inject

/**
 * Presenter of TrendingRepoDetailFragment
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
class TrendingRepoDetailPresenterImpl
@Inject constructor() : BasePresenter<TrendingRepoDetailView, TrendingRepoDetailPresenterImpl.State>() {

    fun prepareGithubRepoDetailsView(githubRepo: GithubRepo) {
        view?.showGithubRepoDetails(githubRepo)
    }

    override fun newState(): State = State()

    @Parcel
    class State : BasePresenter.State
}