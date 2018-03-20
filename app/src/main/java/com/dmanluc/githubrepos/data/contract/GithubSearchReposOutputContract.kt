package com.dmanluc.githubrepos.data.contract

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
data class GithubSearchReposOutputContract(val total_items: String?,
                                           val incomplete_results: String?,
                                           val items: List<GithubRepoOutputContract>?)


