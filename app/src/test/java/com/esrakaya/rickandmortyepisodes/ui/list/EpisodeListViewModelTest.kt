package com.esrakaya.rickandmortyepisodes.ui.list

import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.domain.FetchEpisodeList
import com.esrakaya.rickandmortyepisodes.ui.list.EpisodeListUiEvent.NavigateToDetail
import com.esrakaya.rickandmortyepisodes.ui.list.EpisodeListUiEvent.ShowError
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
class EpisodeListViewModelTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var fetchEpisodeList: FetchEpisodeList

    @InjectMockKs
    private lateinit var episodeListViewModel: EpisodeListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Verify state is correct when the episode list fetched`() =
        coroutineTestRule.runBlockingTest {
            val episodes = mockk<ApolloResponse<EpisodeListQuery.Data>>().data?.episodes
            coEvery { fetchEpisodeList(1) } returns episodes

            episodeListViewModel.fetch()

            coVerify { fetchEpisodeList(1) }
            episodeListViewModel.uiState.value.items shouldBe episodes?.results.orEmpty()
            episodeListViewModel.uiState.value.page shouldBe episodes?.info?.next
        }

    @Test
    fun `Verify state is error when the episode list did not fetch`() =
        coroutineTestRule.runBlockingTest {
            val deferred = async { episodeListViewModel.uiEvent.first() }
            val exception = IllegalStateException("exception!!")
            coEvery { fetchEpisodeList(1) } throws exception

            episodeListViewModel.fetch()

            coVerify { fetchEpisodeList(1) }
            episodeListViewModel.uiState.value.items.shouldBeNull()
            episodeListViewModel.uiState.value.page shouldBe 1
            deferred.await() shouldBe ShowError("exception!!")
        }

    @Test
    fun `Verify event is correct when the episode clicked with not null id`() =
        coroutineTestRule.runBlockingTest {
            val deferred = async { episodeListViewModel.uiEvent.first() }

            episodeListViewModel.onItemClicked("id")

            deferred.await() shouldBe NavigateToDetail("id")
        }

    @Test
    fun `Verify event is correct when the episode clicked with null id`() =
        coroutineTestRule.runBlockingTest {
            val deferred = async { episodeListViewModel.uiEvent.first() }

            episodeListViewModel.onItemClicked(null)

            deferred.await() shouldBe ShowError()
        }

    @Test
    fun `Verify state is correct when the episode list refreshed`() =
        coroutineTestRule.runBlockingTest {
            val episodes = mockk<ApolloResponse<EpisodeListQuery.Data>>().data?.episodes
            coEvery { fetchEpisodeList(1) } returns episodes

            episodeListViewModel.onRefresh()

            coVerify { fetchEpisodeList(1) }
            episodeListViewModel.uiState.value.items shouldBe episodes?.results.orEmpty()
            episodeListViewModel.uiState.value.page shouldBe episodes?.info?.next
        }

}
