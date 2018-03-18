package com.dmanluc.githubrepos.domain.entity

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
data class GithubRepo(val id: Int,
                      val name: String,
                      val ownerName: String,
                      val ownerAvatarUrl: String,
                      val ownerUrl: String,
                      val description: String,
                      val private: Boolean,
                      val contributorsUrl: String,
                      val contributors: List<GithubRepoContributor>?,
                      val createdAt: String,
                      val updatedAt: String,
                      val url: String,
                      val starsRating: Int,
                      val language: String?,
                      val forksNumber: Int,
                      val licenseName: String?)