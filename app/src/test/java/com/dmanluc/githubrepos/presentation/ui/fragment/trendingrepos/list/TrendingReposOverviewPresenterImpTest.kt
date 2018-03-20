package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.list

import com.dmanluc.githubrepos.data.api.GithubApi
import com.dmanluc.githubrepos.data.contract.GithubRepoContributorOutputContract
import com.dmanluc.githubrepos.data.contract.GithubRepoLicenseOutputContract
import com.dmanluc.githubrepos.data.contract.GithubRepoOutputContract
import com.dmanluc.githubrepos.data.contract.GithubRepoOwnerOutputContract
import com.dmanluc.githubrepos.data.contract.GithubSearchReposOutputContract
import com.dmanluc.githubrepos.data.mapper.GithubRepoContributorsMapper
import com.dmanluc.githubrepos.data.mapper.GithubReposMapper
import com.dmanluc.githubrepos.data.repository.GithubRepositoryImpl
import com.dmanluc.githubrepos.domain.interactor.GetGithubTrendingAndroidReposUseCase
import com.dmanluc.githubrepos.domain.repository.GithubRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.junit.runners.model.Statement
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.powermock.core.classloader.annotations.PrepareForTest
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Unit test of TrendingReposOverviewFragment´s presenter implementation
 *
 * @author Daniel Manrique <dmanluc91@gmail.com>
 * @version 1
 * @since 20/3/18.
 */
@PrepareForTest(GithubReposMapper::class, GithubRepoContributorsMapper::class)
@RunWith(MockitoJUnitRunner::class)
class TrendingReposOverviewPresenterImpTest {

    @Mock
    private lateinit var view: TrendingReposOverviewView
    private lateinit var presenter: TrendingReposOverviewPresenterImp
    private lateinit var state: TrendingReposOverviewPresenterImp.State
    @Mock
    private lateinit var service: GithubApi
    private lateinit var repoTransformer: GithubReposMapper
    private lateinit var contributorTransformer: GithubRepoContributorsMapper
    private lateinit var repository: GithubRepositoryImpl
    private lateinit var interactor: GetGithubTrendingAndroidReposUseCase

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repoTransformer = GithubReposMapper()
        contributorTransformer = GithubRepoContributorsMapper()
        repository = GithubRepositoryImpl(service, repoTransformer, contributorTransformer)
        interactor = GetGithubTrendingAndroidReposUseCase(repository)
        presenter = TrendingReposOverviewPresenterImp(interactor)
        state = TrendingReposOverviewPresenterImp.State(GithubRepository.TrendingOption.TODAY)
        presenter.attachView(view)
    }

    @Test
    fun loadGithubTrendingAndroidRepos_withoutRefreshMode_success() {
        val offset = 0

        `when`(service.fetchTrendingAndroidRepos(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(
                Single.just(mockGithubSearchResponseData()))

        val repoServiceResult = service.fetchTrendingAndroidRepos(anyString(), anyString(),
                                                                  anyString(), anyInt(),
                                                                  anyInt()).test().assertComplete()

        `when`(service.fetchRepoContributors(anyString())).thenReturn(
                Single.just(mockGithubContributorResponseData()))

        val contributorServiceResult = service.fetchRepoContributors(anyString()).test().assertComplete()

        presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.TODAY, offset, false)

        val reposToShow = repoTransformer.mapApiContractToDomainModel(repoServiceResult.values()[0])
        reposToShow.forEach {
            it.contributors = contributorTransformer.mapApiContractToDomainModel(contributorServiceResult.values()[0])
        }

        verify(view, Mockito.times(1)).showLoadingProgress()
        verify(view, Mockito.times(1)).hideLoadingProgress()
        verify(view).handleEmptyView(false)
        verify(view).handleFloatingMenu(true)
        verify(view).showGithubRepoList(reposToShow, false)
    }

    @Test
    fun loadGithubTrendingAndroidRepos_withRefreshMode_success() {
        val offset = 30

        `when`(service.fetchTrendingAndroidRepos(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(
                Single.just(mockGithubSearchResponseData()))

        val repoServiceResult = service.fetchTrendingAndroidRepos(anyString(), anyString(),
                                                                  anyString(), anyInt(),
                                                                  anyInt()).test().assertComplete()

        `when`(service.fetchRepoContributors(anyString())).thenReturn(
                Single.just(mockGithubContributorResponseData()))

        val contributorServiceResult = service.fetchRepoContributors(anyString()).test().assertComplete()

        presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.TODAY, offset, true)

        val reposToShow = repoTransformer.mapApiContractToDomainModel(repoServiceResult.values()[0])
        reposToShow.forEach {
            it.contributors = contributorTransformer.mapApiContractToDomainModel(contributorServiceResult.values()[0])
        }

        verify(view).handleEmptyView(false)
        verify(view).handleFloatingMenu(true)
        verify(view).showGithubRepoList(reposToShow, true)
    }

    @Test
    fun loadGithubTrendingAndroidRepos_emptyView_success() {
        val offset = 30

        `when`(service.fetchTrendingAndroidRepos(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(
                Single.just(mockEmptyGithubSearchResponseData()))

        val emptyRepoServiceResult = service.fetchTrendingAndroidRepos(anyString(), anyString(),
                                                                       anyString(), anyInt(),
                                                                       anyInt()).test().assertComplete()

        presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.TODAY, offset, false)

        verify(view, Mockito.times(1)).showLoadingProgress()
        verify(view, Mockito.times(1)).hideLoadingProgress()
        verify(view).handleEmptyView(true)
        verify(view).handleFloatingMenu(true)
    }

    @Test
    fun loadGithubTrendingAndroidRepos_error() {
        val offset = 20
        val errorException = Throwable("Error")

        `when`(service.fetchTrendingAndroidRepos(anyString(), anyString(), anyString(), anyInt(), anyInt())).thenReturn(
                Single.error(errorException))

        val result = service.fetchTrendingAndroidRepos(anyString(), anyString(),
                                                       anyString(), anyInt(),
                                                       anyInt()).test().assertError(errorException)

        presenter.loadGithubTrendingAndroidRepos(GithubRepository.TrendingOption.TODAY, offset, false)

        verify(view, Mockito.times(1)).showLoadingProgress()
        verify(view, Mockito.times(1)).hideLoadingProgress()
        verify(view, Mockito.times(1)).showGithubApiErrorMessage("Error")
    }

    private fun mockGithubSearchResponseData(): GithubSearchReposOutputContract {
        val listItems = listOf(mockGithubRepo(1), mockGithubRepo(2))
        return GithubSearchReposOutputContract("2", incomplete_results = "0", items = listItems)
    }

    private fun mockEmptyGithubSearchResponseData(): GithubSearchReposOutputContract {
        val listItems = listOf<GithubRepoOutputContract>()
        return GithubSearchReposOutputContract("2", incomplete_results = "0", items = listItems)
    }

    private fun mockGithubRepo(id: Int) = GithubRepoOutputContract(
            id = id,
            name = "name",
            full_name = "fullname",
            owner = GithubRepoOwnerOutputContract(id, login = "login", avatar_url = "avatarUrl", html_url = "url"),
            private = false,
            html_url = "url",
            created_at = "01-01-2018",
            updated_at = "01-02-2018",
            stargazers_count = 1000,
            language = "Kotlin",
            forks_count = 100,
            license = GithubRepoLicenseOutputContract(key = id.toString(), name = "license", url = "url"),
            score = 35.7,
            description = "description",
            contributors_url = "url")

    private fun mockGithubContributorResponseData(): List<GithubRepoContributorOutputContract> {
        return listOf<GithubRepoContributorOutputContract>(mockGithubRepoContributor(1), mockGithubRepoContributor(2))
    }

    private fun mockGithubRepoContributor(id: Int) = GithubRepoContributorOutputContract(
            id = id,
            login = "name",
            avatar_url = "avatar",
            html_url = "url")

    class RxImmediateSchedulerRule : TestRule {
        private val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                // this prevents StackOverflowErrors when scheduling with a delay
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Scheduler.Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        override fun apply(base: Statement, description: Description): Statement {
            return object : Statement() {
                @Throws(Throwable::class)
                override fun evaluate() {
                    RxJavaPlugins.setInitIoSchedulerHandler { immediate }
                    RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
                    RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
                    RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
                    RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }

                    try {
                        base.evaluate()
                    } finally {
                        RxJavaPlugins.reset()
                        RxAndroidPlugins.reset()
                    }
                }
            }
        }
    }
}