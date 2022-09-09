package com.esrakaya.rickandmortyepisodes.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.data.EpisodeRepository
import com.esrakaya.rickandmortyepisodes.utils.CoroutineTestRule
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FetchEpisodeListTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var episodeRepository: EpisodeRepository

    @InjectMockKs
    private lateinit var fetchEpisodeList: FetchEpisodeList

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke() = coroutineTestRule.runBlockingTest {
        val response = mockk<ApolloResponse<EpisodeListQuery.Data>>()
        coEvery { episodeRepository.getEpisodeList(1) } returns response

        val result = fetchEpisodeList(1)

        coVerify { episodeRepository.getEpisodeList(1) }
        result shouldBe response.data?.episodes
    }
}
