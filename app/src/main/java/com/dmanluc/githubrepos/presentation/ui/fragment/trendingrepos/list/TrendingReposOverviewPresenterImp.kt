package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.interactor.BaseObserver
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidRepos
import com.dmanluc.githubrepos.presentation.base.BasePresenter
import org.parceler.Parcel
import javax.inject.Inject

/**
 * Presenter of TrendingReposOverviewFragment
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
class TrendingReposOverviewPresenterImp
@Inject constructor(
        private val githubTrendingAndroidRepos: GetGithubTrendingAndroidRepos) : BasePresenter<TrendingReposOverviewView, TrendingReposOverviewPresenterImp.State>
                                                                     () {

    fun loadGithubTrendingAndroidRepos(offset: Int, refreshMode: Boolean) {
        if (!refreshMode && isViewAttached()) view?.showLoadingProgress()

        githubTrendingAndroidRepos.execute(object : BaseObserver<List<GithubRepo>>() {
            override fun onSuccess(t: List<GithubRepo>) {
                super.onSuccess(t)
                if (!refreshMode) view?.hideLoadingProgress()
                view?.showGithubRepoList(t, offset > 0)
            }

            override fun onErrorMessage(errorMessage: String?) {
                super.onErrorMessage(errorMessage)
                view?.hideLoadingProgress()
                view?.showGithubApiErrorMessage(errorMessage)
            }
        }, Pair((offset/30) + 1, 30))
    }

    override fun newState(): State = State()

    @Parcel
    class State : BasePresenter.State
}