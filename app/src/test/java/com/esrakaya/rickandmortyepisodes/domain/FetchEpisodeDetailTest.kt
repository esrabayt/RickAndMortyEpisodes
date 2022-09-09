package com.esrakaya.rickandmortyepisodes.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeQuery
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
class FetchEpisodeDetailTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var episodeRepository: EpisodeRepository

    @InjectMockKs
    private lateinit var fetchEpisodeDetail: FetchEpisodeDetail

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun invoke() = coroutineTestRule.runBlockingTest {
        val response = mockk<ApolloResponse<EpisodeQuery.Data>>()
        coEvery { episodeRepository.getEpisode("id") } returns response

        val result = fetchEpisodeDetail("id")

        coVerify { episodeRepository.getEpisode("id") }
        result shouldBe response.data?.episode
    }
}
