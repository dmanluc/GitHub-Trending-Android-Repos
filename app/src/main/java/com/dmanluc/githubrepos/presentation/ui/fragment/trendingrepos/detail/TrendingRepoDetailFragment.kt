package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.R.layout.fragment_github_repo_detail
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.presentation.base.BaseFragment
import com.dmanluc.githubrepos.presentation.di.component.DaggerTrendingRepoDetailFragmentComponent
import com.dmanluc.githubrepos.presentation.ui.activity.trendingrepos.TrendingReposActivity
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar1
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar2
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar3
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar4
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar5
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progressBar6
import kotlinx.android.synthetic.main.fragment_github_repo_detail.progress_bar_owner_avatar
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_1
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_2
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_3
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_4
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_5
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributor_avatar_6
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_contributors_avatar_not_found_info
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_created_at
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_description
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_language
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_link_button_github
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_owner_avatar
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_owner_link_button_github
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_owner_name
import kotlinx.android.synthetic.main.fragment_github_repo_detail.repository_detail_updated_at
import org.parceler.Parcels
import java.net.URL
import javax.inject.Inject

/**
 * Fragment to display the details of the github repo previously selected from the list of trending android repositories
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
/**

 *
 * @author Daniel Manrique Lucas
 */
class TrendingRepoDetailFragment :
        BaseFragment<TrendingRepoDetailView, TrendingRepoDetailPresenterImpl, BaseFragment.BaseCallback>(),
        TrendingRepoDetailView {

    @Inject
    lateinit var internalPresenter: TrendingRepoDetailPresenterImpl

    override val layoutId: Int
        get() = fragment_github_repo_detail
    override var screenTitle: String = ""
    override val presenter: TrendingRepoDetailPresenterImpl
        get() = internalPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Parcels.unwrap<GithubRepo>(arguments?.getParcelable<Parcelable>(KEY_REPOSITORY_SELECTED)).let {
            presenter.prepareGithubRepoDetailsView(it)
        }
    }

    override fun showBackArrow(): Boolean = true

    override fun provideDaggerDependency() {
        DaggerTrendingRepoDetailFragmentComponent.builder()
                .appComponent(appComponent)
                .build().inject(this)
    }

    override fun showGithubRepoDetails(githubRepo: GithubRepo) {
        val contributorsAvatarIds = listOf(repository_detail_contributor_avatar_1,
                                           repository_detail_contributor_avatar_2,
                                           repository_detail_contributor_avatar_3,
                                           repository_detail_contributor_avatar_4,
                                           repository_detail_contributor_avatar_5,
                                           repository_detail_contributor_avatar_6)

        val contributorsProgressBarAvatarIds = listOf(progressBar1,
                                                      progressBar2,
                                                      progressBar3,
                                                      progressBar4,
                                                      progressBar5,
                                                      progressBar6)

        (activity as TrendingReposActivity).setActionBar(githubRepo.name, showBackArrow())

        repository_detail_owner_name.text = githubRepo.ownerName
        Glide.with(context)
                .load(URL(githubRepo.ownerAvatarUrl))
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                              isFirstResource: Boolean): Boolean {
                        progress_bar_owner_avatar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        progress_bar_owner_avatar.visibility = View.GONE
                        return false
                    }
                })
                .into(repository_detail_owner_avatar)

        if (githubRepo.description.isBlank()) {
            repository_detail_description.text = getString(R.string.repository_without_description_info)
        } else {
            repository_detail_description.text = githubRepo.description
        }

        if (githubRepo.contributors?.isNotEmpty() == true) {
            repository_detail_contributors_avatar_not_found_info.visibility = View.GONE
            githubRepo.contributors?.take(6)?.forEachIndexed { index, githubRepoContributor ->
                contributorsProgressBarAvatarIds[index].visibility = View.VISIBLE
                Glide.with(context)
                        .load(URL(githubRepoContributor.avatarUrl))
                        .listener(object : RequestListener<Drawable> {
                            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
                                                      isFirstResource: Boolean): Boolean {
                                contributorsProgressBarAvatarIds[index].visibility = View.GONE
                                return false
                            }

                            override fun onResourceReady(resource: Drawable?, model: Any?,
                                                         target: Target<Drawable>?,
                                                         dataSource: DataSource?,
                                                         isFirstResource: Boolean): Boolean {
                                contributorsProgressBarAvatarIds[index].visibility = View.GONE
                                contributorsAvatarIds[index].visibility = View.VISIBLE
                                return false
                            }
                        })
                        .into(contributorsAvatarIds.get(index = index))
            }
        } else {
            contributorsProgressBarAvatarIds.forEach { it.visibility = View.GONE }
            contributorsAvatarIds.forEach { it.visibility = View.GONE }
            repository_detail_contributors_avatar_not_found_info.visibility = View.VISIBLE
        }

        repository_detail_created_at.text = githubRepo.createdAt
        repository_detail_updated_at.text = githubRepo.updatedAt

        if (githubRepo.language?.isBlank() == true) {
            repository_detail_language.text = getString(R.string.repository_without_language_info)
        } else {
            repository_detail_language.text = githubRepo.language
        }

        githubRepo.let { setUpListenerGithubRepoLinkResources(it) }
    }

    private fun setUpListenerGithubRepoLinkResources(githubRepo: GithubRepo) {
        repository_detail_owner_link_button_github.setOnClickListener {
            githubRepo.ownerUrl.navigateToUrl(context ?: activity as TrendingReposActivity)
        }
        repository_detail_link_button_github.setOnClickListener {
            githubRepo.url.navigateToUrl(context ?: activity as TrendingReposActivity)
        }
    }

    private fun String.navigateToUrl(context: Context) {
        val prefixInsecureUrl = "http://"
        val prefixSecureUrl = "https://"
        var websiteUrl = this

        if (!this.startsWith(prefixSecureUrl) && !this.startsWith(prefixInsecureUrl)) {
            websiteUrl = prefixInsecureUrl + this
        }

        val openUrlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))

        openUrlIntent.resolveActivity(context.packageManager)?.let { startActivity(openUrlIntent) }
    }

    companion object {

        const val KEY_REPOSITORY_SELECTED = "key:bundle:github_repository"

        fun newInstance(githubRepo: GithubRepo) = TrendingRepoDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelable(KEY_REPOSITORY_SELECTED, Parcels.wrap(githubRepo))
            }
        }
    }

}