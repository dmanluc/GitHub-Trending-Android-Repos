package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.adapter.TrendingReposOverviewAdapter
import com.dmanluc.githubrepos.presentation.base.BaseFragment
import com.dmanluc.githubrepos.presentation.custom.EndlessRecyclerViewScrollListener
import com.dmanluc.githubrepos.presentation.di.component.DaggerTrendingReposOverviewFragmentComponent
import com.dmanluc.githubrepos.presentation.di.module.TrendingReposOverviewModule
import kotlinx.android.synthetic.main.fragment_github_repo_list.recycler
import kotlinx.android.synthetic.main.fragment_github_repo_list.swipe_layout
import javax.inject.Inject

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
class TrendingReposOverviewFragment :
        BaseFragment<TrendingReposOverviewView, TrendingReposOverviewPresenterImp, TrendingReposOverviewFragment.Callback>(),
        TrendingReposOverviewView, SwipeRefreshLayout.OnRefreshListener, TrendingReposOverviewAdapter.Listener {

    @Inject
    lateinit var internalPresenter: TrendingReposOverviewPresenterImp
    @Inject
    lateinit var adapter: TrendingReposOverviewAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override val layoutId
        get() = R.layout.fragment_github_repo_list
    override var screenTitle = ""
        get() = getString(R.string.app_name)
    override val presenter
        get() = internalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showLoadingProgress()
        presenter.loadGithubTrendingAndroidRepos(0, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
    }

    override fun showBackArrow(): Boolean = false

    override fun provideDaggerDependency() {
        DaggerTrendingReposOverviewFragmentComponent.builder()
                .appComponent(appComponent)
                .trendingReposOverviewModule(TrendingReposOverviewModule(this))
                .build().inject(this)
    }

    override fun showGithubRepoList(githubRepos: List<GithubRepo>, moreLoadingMode: Boolean) {
        adapter.setAdapterItems(githubRepos, moreLoadingMode)
        if (swipe_layout.isRefreshing) swipe_layout.isRefreshing = false
    }

    override fun onGithubRepoSelected(githubRepo: GithubRepo) {
        callback?.onGithubRepoSelected(githubRepo)
    }

    override fun onRefresh() {
        presenter.loadGithubTrendingAndroidRepos(0, true)
        scrollListener.resetState()
    }

    override fun showLoadingProgress() {
        callback?.showLoadingProgress()
    }

    override fun hideLoadingProgress() {
        callback?.hideLoadingProgress()
    }

    override fun showGithubApiErrorMessage(errorMessage: String?) {
        if (swipe_layout.isRefreshing) swipe_layout.isRefreshing = false
        errorMessage?.let { Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show() }
    }

    private fun configureView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        recycler.layoutManager = linearLayoutManager
        recycler.adapter = adapter
        recycler.setHasFixedSize(true)

        scrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.loadGithubTrendingAndroidRepos(adapter.itemCount, false)
            }
        }

        recycler.addOnScrollListener(scrollListener)

        swipe_layout.setOnRefreshListener(this)
        swipe_layout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)
    }

    interface Callback : BaseFragment.BaseCallback {

        fun showLoadingProgress()

        fun hideLoadingProgress()

        fun onGithubRepoSelected(githubRepo: GithubRepo)

    }

    companion object {

        fun newInstance() = TrendingReposOverviewFragment()

    }

}