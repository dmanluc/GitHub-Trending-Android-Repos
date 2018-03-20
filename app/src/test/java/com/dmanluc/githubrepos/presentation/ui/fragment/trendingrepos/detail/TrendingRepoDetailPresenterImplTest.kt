package com.dmanluc.githubrepos.presentation.ui.fragment.trendingrepos.detail

import com.dmanluc.githubrepos.domain.entity.GithubRepo
import com.dmanluc.githubrepos.domain.entity.GithubRepoContributor
import org.easymock.EasyMock
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.api.easymock.PowerMock
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

/**
 * Unit test of TrendingRepoDetailFragment´s presenter implementation
 *
 * @author Daniel Manrique <daniel.manrique>@uxsmobile.com>
 * @version 1
 * @since 20/3/18.
 */
@PrepareForTest()
@RunWith(PowerMockRunner::class)
class TrendingRepoDetailPresenterImplTest {

    private lateinit var view: TrendingRepoDetailView
    private lateinit var presenter: TrendingRepoDetailPresenterImpl
    private lateinit var state: TrendingRepoDetailPresenterImpl.State

    @Before
    fun setUp() {
        view = PowerMock.createMock(TrendingRepoDetailView::class.java)
        presenter = TrendingRepoDetailPresenterImpl()
        state = TrendingRepoDetailPresenterImpl.State()
    }

    @After
    fun cleanUp() {
        PowerMock.verifyAll()
        PowerMock.resetAll()
    }

    @Test
    fun prepareGithubRepoDetailsView_showGithubRepoDetails() {
        val githubRepo = mockGithubRepo(3)
        view.showGithubRepoDetails(githubRepo)
        EasyMock.expect(presenter.isViewAttached()).anyTimes()

        PowerMock.replayAll()

        presenter.attachView(view)
        presenter.prepareGithubRepoDetailsView(githubRepo)
    }

    private fun mockGithubRepo(id: Int) = GithubRepo(
            id = id,
            name = "name",
            fullName = "fullname",
            ownerName = "login",
            ownerAvatarUrl = "url",
            ownerUrl = "url",
            isPrivate = false,
            url = "url",
            createdAt = "01-01-2018",
            updatedAt = "01-02-2018",
            starsRating = 1000,
            language = "Kotlin",
            forksNumber = 100,
            licenseName = "license",
            description = "description",
            contributorsUrl = "url",
            contributors = mutableListOf(
                    GithubRepoContributor(id = id, loginName = "login", avatarUrl = "url", url = "url")))

}