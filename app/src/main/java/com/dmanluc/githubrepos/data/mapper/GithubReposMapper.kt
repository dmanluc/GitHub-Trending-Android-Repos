package com.dmanluc.githubrepos.data.mapper

import com.dmanluc.githubrepos.data.contract.GithubSearchReposOutputContract
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * @author   Daniel Manrique <dmanluc91@gmail.com>
 * @version  1
 * @since    18/3/18.
 */
open class GithubReposMapper : Mapper<GithubSearchReposOutputContract, GithubRepo> {

    override fun mapApiContractToDomainModel(outputContract: GithubSearchReposOutputContract): List<GithubRepo> =
            outputContract.items?.map { r ->
                GithubRepo(
                        id = r.id ?: 0,
                        name = r.name.orEmpty(),
                        fullName = r.full_name.orEmpty(),
                        ownerName = r.owner?.login.orEmpty(),
                        ownerAvatarUrl = r.owner?.avatar_url.orEmpty(),
                        ownerUrl = r.owner?.html_url.orEmpty(),
                        description = r.description.orEmpty(),
                        isPrivate = r.private ?: false,
                        contributorsUrl = r.contributors_url.orEmpty(),
                        contributors = mutableListOf(),
                        createdAt = r.created_at.orEmpty().isoDateToPrettyFormat(),
                        updatedAt = r.updated_at.orEmpty().isoDateToPrettyFormat(),
                        url = r.html_url.orEmpty(),
                        starsRating = r.stargazers_count ?: 0,
                        language = r.language.orEmpty(),
                        forksNumber = r.forks_count ?: 0,
                        licenseName = r.license?.name.orEmpty())
            } ?: emptyList()

    fun String.isoDateToPrettyFormat(): String {
        return try {
            val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            val prettyFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            prettyFormat.format(isoDateFormat.parse(this))
        } catch (e: IllegalArgumentException) {
            this
        } catch (e: ParseException) {
            this
        }
    }

}

