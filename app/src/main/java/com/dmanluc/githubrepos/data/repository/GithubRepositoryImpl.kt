package com.dmanluc.githubrepos.data.repository

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.mapper.GithubRepoContributorsMapper
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.entity.GithubRepoContributor
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import io.reactivex.Single
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * Github repository implementation
 *
 * @author   Daniel Manrique <daniel.manrique@uxsmobile.com>
 * @version  1
 * @since    17/3/18.
 */
class GithubRepositoryImpl
@Inject constructor(private val api: GithubApi,
                    private val reposMapper: GithubReposMapper,
                    private val contributorsMapper: GithubRepoContributorsMapper) : GithubRepository {

    override fun getTrendingAndroidRepos(trendingOption: GithubRepository.TrendingOption, page: Int,
                                         itemsPerPage: Int): Single<List<GithubRepo>> {
        val query = "android created:>=${getCalculatedDate(trendingOption)}"
        val sortType = "stars"
        val orderType = "desc"

        return api.fetchTrendingAndroidRepos(query,
                                             sortType,
                                             orderType,
                                             page,
                                             itemsPerPage)
                .map { reposMapper.mapApiContractToDomainModel(it) }
                .map {
                    it.map {
                        it.copy(contributors = api.fetchRepoContributors(it.contributorsUrl)
                                .onErrorResumeNext(Single.just(mutableListOf()))
                                .map {
                                    contributorsMapper.mapApiContractToDomainModel(it)
                                }.blockingGet())
                    }
                }
    }

    private fun getCalculatedDate(dateType: GithubRepository.TrendingOption): String {
        val currentCalendar = Calendar.getInstance()
        currentCalendar.firstDayOfWeek = Calendar.MONDAY
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        when (dateType) {
            GithubRepository.TrendingOption.TODAY -> {
            }
            GithubRepository.TrendingOption.THIS_WEEK -> {
                currentCalendar.set(Calendar.DAY_OF_WEEK, currentCalendar.firstDayOfWeek)
                return dateFormat.format(Date(currentCalendar.timeInMillis))
            }
            GithubRepository.TrendingOption.THIS_MONTH -> {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 1)
                return dateFormat.format(Date(currentCalendar.timeInMillis))
            }
            GithubRepository.TrendingOption.THIS_YEAR -> {
                currentCalendar.set(Calendar.DAY_OF_MONTH, 1)
                currentCalendar.set(Calendar.MONTH, 0)
                return dateFormat.format(Date(currentCalendar.timeInMillis))
            }
        }

        return dateFormat.format(Date(currentCalendar.timeInMillis))
    }

    fun GithubRepo.addContributors(contributors: List<GithubRepoContributor>) {
        this.contributors?.addAll(contributors)
    }

}