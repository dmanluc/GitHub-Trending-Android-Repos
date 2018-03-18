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
import kotlinx.android.synthetic.main.item_github_repo_list.view.progressBar
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_description
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_forks
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_name
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_owner_avatar
import kotlinx.android.synthetic.main.item_github_repo_list.view.repository_stars
import java.net.URL
import javax.inject.Inject

/**
 * Adapter which prepares list of characters to be shown in the UI
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
class TrendingReposOverviewAdapter @Inject constructor(
        private val context: Context, private val listener: Listener) : RecyclerView.Adapter<TrendingReposOverviewAdapter.GithubViewHolder>() {

    private val items: MutableList<GithubRepo> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        return GithubViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_github_repo_list, parent, false))
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
            Glide.with(context)
                    .load(URL(item.ownerAvatarUrl))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            itemView.progressBar.visibility = View.GONE
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                     dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            itemView.progressBar.visibility = View.GONE
                            return false
                        }
                    })
                    .into(itemView.repository_owner_avatar)

            itemView.repository_name.text = item.name
            itemView.repository_description.text = item.description
            itemView.repository_stars.text = item.starsRating.toString()
            itemView.repository_forks.text = item.forksNumber.toString()

            itemView.setOnClickListener { listener.onGithubRepoSelected(item) }
        }
    }

    interface Listener {

        fun onGithubRepoSelected(githubRepo: GithubRepo)

    }

}