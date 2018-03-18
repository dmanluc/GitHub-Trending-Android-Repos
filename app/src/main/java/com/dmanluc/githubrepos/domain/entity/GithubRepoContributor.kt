package com.dmanluc.githubrepos.domain.entity

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
data class GithubRepoContributor(val id: String,
                                 val loginName: String,
                                 val avatarUrl: String,
                                 val url: String)