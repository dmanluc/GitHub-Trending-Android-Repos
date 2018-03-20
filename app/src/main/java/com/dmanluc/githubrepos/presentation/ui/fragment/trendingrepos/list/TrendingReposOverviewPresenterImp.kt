package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.interactor.BaseObserver
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidReposUseCase
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import com.dmanluc.githubrepos.presentation.base.BasePresenter
import org.parceler.Parcel
import org.parceler.ParcelConstructor
import javax.inject.Inject

/**
 * Presenter of TrendingReposOverviewFragment
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
class TrendingReposOverviewPresenterImp
@Inject constructor(
        private val githubTrendingAndroidReposUseCase: GetGithubTrendingAndroidReposUseCase) :
        BasePresenter<TrendingReposOverviewView, TrendingReposOverviewPresenterImp.State>
        () {

    fun loadGithubTrendingAndroidRepos(
            trendingOption: GithubRepository.TrendingOption = GithubRepository.TrendingOption.TODAY, offset: Int,
            refreshMode: Boolean) {
        if (!refreshMode && isViewAttached()) view?.showLoadingProgress()

        getState().trendingOption = trendingOption

        githubTrendingAndroidReposUseCase.execute(object : BaseObserver<List<GithubRepo>>() {
            override fun onSuccess(t: List<GithubRepo>) {
                super.onSuccess(t)
                if (!refreshMode) view?.hideLoadingProgress()
                view?.handleFloatingMenu(true)
                if (t.isEmpty()) {
                    view?.handleEmptyView(true)
                } else {
                    view?.handleEmptyView(false)
                    view?.showGithubRepoList(t, offset > 0 && trendingOption == getState().trendingOption)
                }
            }

            override fun onErrorMessage(errorMessage: String?) {
                super.onErrorMessage(errorMessage)
                view?.hideLoadingProgress()
                view?.handleFloatingMenu(false)
                view?.showGithubApiErrorMessage(errorMessage)
            }
        }, Pair(trendingOption, Pair((offset / 30) + 1, 30)))
    }

    override fun newState(): State = State(GithubRepository.TrendingOption.TODAY)

    @Parcel
    data class State @ParcelConstructor constructor(var trendingOption: GithubRepository.TrendingOption) :
            BasePresenter.State
}