package com.dmanluc.githubrepos.data.contract

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
data class GithubSearchReposOutputContract(val total_items: String?,
                                           val incomplete_results: String?,
                                           val items: List<GithubRepoOutputContract>?)


