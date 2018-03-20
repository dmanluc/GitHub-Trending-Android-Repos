package com.dmanluc.githubrepos.data.mapper

import com.dmanluc.githubrepos.data.contract.GithubRepoContributorOutputContract
import com.dmanluc.githubrepos.domain.entity.GithubRepoContributor

/**
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    18/3/18.
 */
open class GithubRepoContributorsMapper : Mapper<List<GithubRepoContributorOutputContract>, GithubRepoContributor> {

    override fun mapApiContractToDomainModel(outputData: List<GithubRepoContributorOutputContract>): MutableList<GithubRepoContributor> =
            outputData.map { c ->
                GithubRepoContributor(
                        id = c.id ?: 0,
                        loginName = c.login.orEmpty(),
                        avatarUrl = c.avatar_url.orEmpty(),
                        url = c.html_url.orEmpty())
            }.toMutableList()

}