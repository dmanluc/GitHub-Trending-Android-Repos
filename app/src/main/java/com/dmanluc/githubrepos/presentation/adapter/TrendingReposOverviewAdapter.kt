package com.dmanluc.githubrepos.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dmanluc.githubrepos.R
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar1
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar2
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar3
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar4
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar5
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar6
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_1
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_2
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_3
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_4
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_5
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_contributor_avatar_6
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_description
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_forks
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_name
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_stars
import javax.inject.Inject

/**
 * Adapter which prepares list of repositories to be shown in the UI
 *
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
class TrendingReposOverviewAdapter @Inject constructor(
        private val context: Context, private val listener: Listener) :
        RecyclerView.Adapter<TrendingReposOverviewAdapter.GithubViewHolder>() {

    private val items: MutableList<GithubRepo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        return GithubViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_github_repo_list, parent, false))
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    fun setAdapterItems(githubRepos: List<GithubRepo>, updateMode: Boolean) {
        if (!updateMode) {
            this.items.clear()
        }
        this.items.addAll(githubRepos)
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: GithubRepo) {
            val contributorsAvatarIds = listOf(itemView.repository_contributor_avatar_1,
                                               itemView.repository_contributor_avatar_2,
                                               itemView.repository_contributor_avatar_3,
                                               itemView.repository_contributor_avatar_4,
                                               itemView.repository_contributor_avatar_5,
                                               itemView.repository_contributor_avatar_6)

            val contributorsProgressBarAvatarIds = listOf(itemView.progressBar1,
                                                          itemView.progressBar2,
                                                          itemView.progressBar3,
                                                          itemView.progressBar4,
                                                          itemView.progressBar5,
                                                          itemView.progressBar6)

            itemView.repository_name.text = item.fullName
            if (item.description.isBlank()) itemView.repository_description.text = context.getString(
                    R.string.repository_without_description_info)
            else {
                itemView.repository_description.text = item.description
            }
            itemView.repository_stars.text = item.starsRating.toString()
            itemView.repository_forks.text = item.forksNumber.toString()

            if (item.contributors?.isNotEmpty() == true) {
                item.contributors?.take(6)?.forEachIndexed { index, githubRepoContributor ->
                    contributorsProgressBarAvatarIds[index].visibility = View.VISIBLE
                    Glide.with(context)
                            .load(githubRepoContributor.avatarUrl)
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
            }

            itemView.setOnClickListener { listener.onGithubRepoSelected(item) }
        }
    }


    interface Listener {

        fun onGithubRepoSelected(githubRepo: GithubRepo)

    }

}