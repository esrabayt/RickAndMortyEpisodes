package com.esrakaya.rickandmortyepisodes.ui.detail

import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeQuery
import com.esrakaya.rickandmortyepisodes.domain.FetchEpisodeDetail
import com.esrakaya.rickandmortyepisodes.utils.CoroutineTestRule
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class EpisodeDetailViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var fetchEpisodeDetail: FetchEpisodeDetail

    @InjectMockKs
    private lateinit var episodeDetailViewModel: EpisodeDetailViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Verify state is correct when the episode detail fetched`() =
        coroutineTestRule.runBlockingTest {
            val episode = mockk<ApolloResponse<EpisodeQuery.Data>>().data?.episode
            coEvery { fetchEpisodeDetail("id") } returns episode

            episodeDetailViewModel.fetch("id")

            coVerify { fetchEpisodeDetail("id") }
            episodeDetailViewModel.uiState.value.episode shouldBe episode
        }

    @Test
    fun `Verify state is error when the episode detail did not fetch`() =
        coroutineTestRule.runBlockingTest {
            val deferred = async { episodeDetailViewModel.uiEvent.first() }
            val exception = IllegalStateException("exception!!")
            coEvery { fetchEpisodeDetail("id") } throws exception

            episodeDetailViewModel.fetch("id")

            coVerify { fetchEpisodeDetail("id") }
            episodeDetailViewModel.uiState.value.episode.shouldBeNull()
            deferred.await() shouldBe EpisodeDetailUiEvent.ShowError("exception!!")
        }
}
