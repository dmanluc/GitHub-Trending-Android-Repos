package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import com.dmanluc.githubrepos.presentation.adapter.TrendingReposOverviewAdapter
import com.dmanluc.githubrepos.presentation.base.BaseFragment
import com.dmanluc.githubrepos.presentation.custom.EndlessRecyclerViewScrollListener
import com.dmanluc.githubrepos.presentation.di.component.DaggerTrendingReposOverviewFragmentComponent
import com.dmanluc.githubrepos.presentation.di.module.TrendingReposOverviewModule
import kotlinx.android.synthetic.main.fragment_github_repo_list.fab_menu
import kotlinx.android.synthetic.main.fragment_github_repo_list.fab_repository_this_month
import kotlinx.android.synthetic.main.fragment_github_repo_list.fab_repository_this_week
import kotlinx.android.synthetic.main.fragment_github_repo_list.fab_repository_this_year
import kotlinx.android.synthetic.main.fragment_github_repo_list.fab_repository_today
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
        presenter.loadGithubTrendingAndroidRepos(offset = 0, refreshMode = false)
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
        if (!moreLoadingMode) recycler.layoutManager.scrollToPosition(0)
        if (swipe_layout.isRefreshing) swipe_layout.isRefreshing = false
    }

    override fun onGithubRepoSelected(githubRepo: GithubRepo) {
        callback?.onGithubRepoSelected(githubRepo)
    }

    override fun onRefresh() {
        presenter.loadGithubTrendingAndroidRepos(presenter.getState().trendingOption, offset = 0, refreshMode = true)
        scrollListener.resetState()
    }

    override fun showLoadingProgress() {
        callback?.showLoadingProgress()
    }

    override fun hideLoadingProgress() {
        callback?.hideLoadingProgress()
    }

    override fun handleFloatingMenu(enable: Boolean) {
        if (enable) fab_menu.showMenu(true) else fab_menu.hideMenu(true)
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
                presenter.loadGithubTrendingAndroidRepos(presenter.getState().trendingOption, offset = adapter.itemCount, refreshMode = false)
            }
        }

        recycler.addOnScrollListener(scrollListener)
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) {
                    fab_menu.hideMenu(true)
                } else {
                    fab_menu.showMenu(true)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        swipe_layout.setOnRefreshListener(this)
        swipe_layout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3)

        fab_repository_today.setOnClickListener {
            presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.TODAY, 0, false)
            fab_menu.close(true)
        }
        fab_repository_this_week.setOnClickListener {
            presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.THIS_WEEK, 0, false)
            fab_menu.close(true)
        }
        fab_repository_this_month.setOnClickListener {
            presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.THIS_MONTH, 0, false)
            fab_menu.close(true)
        }
        fab_repository_this_year.setOnClickListener {
            presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.THIS_YEAR, 0, false)
            fab_menu.close(true)
        }
        fab_menu.setOnMenuToggleListener {
            if (it) {
                when (presenter.getState().trendingOption) {
                    GithubRepository.TrendingOption.TODAY -> {
                        fab_repository_today.setColorNormalResId(R.color.fab_label_selected)
                        fab_repository_today.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected))
                        fab_repository_this_week.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_week.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_month.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_month.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_year.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_year.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                    }
                    GithubRepository.TrendingOption.THIS_WEEK -> {
                        fab_repository_this_week.setColorNormalResId(R.color.fab_label_selected)
                        fab_repository_this_week.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected))
                        fab_repository_today.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_today.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_month.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_month.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_year.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_year.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                    }
                    GithubRepository.TrendingOption.THIS_MONTH -> {
                        fab_repository_this_month.setColorNormalResId(R.color.fab_label_selected)
                        fab_repository_this_month.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected))
                        fab_repository_today.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_today.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_week.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_week.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_year.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_year.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                    }
                    GithubRepository.TrendingOption.THIS_YEAR -> {
                        fab_repository_this_year.setColorNormalResId(R.color.fab_label_selected)
                        fab_repository_this_year.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected),
                                ContextCompat.getColor(activity as Context, R.color.fab_label_selected))
                        fab_repository_today.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_today.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_week.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_week.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                        fab_repository_this_month.setColorNormalResId(R.color.colorPrimary)
                        fab_repository_this_month.setLabelColors(
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary),
                                ContextCompat.getColor(activity as Context, R.color.colorPrimary))
                    }
                }
            }
        }
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