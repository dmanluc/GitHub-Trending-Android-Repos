package com.dmanluc.githubrepos.data.contract

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
data class GithubRepoOutputContract(val id: Int?,
                                    val name: String?,
                                    val full_name: String?,
                                    val owner: GithubRepoOwnerOutputContract?,
                                    val private: Boolean?,
                                    val html_url: String?,
                                    val description: String?,
                                    val contributors_url: String?,
                                    val created_at: String?,
                                    val updated_at: String?,
                                    val stargazers_count: Int?,
                                    val language: String?,
                                    val forks_count: Int?,
                                    val license: GithubRepoLicenseOutputContract?,
                                    val score: Double?)