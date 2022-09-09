package com.esrakaya.rickandmortyepisodes.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.EpisodeQuery
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
class EpisodeRepositoryTest {

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @MockK
    private lateinit var apolloClient: ApolloClient

    @InjectMockKs
    private lateinit var episodeRepository: EpisodeRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Verify episode list when the episode list fetched`() =
        coroutineTestRule.runBlockingTest {
            val response = mockk<ApolloResponse<EpisodeListQuery.Data>>()
            coEvery { apolloClient.query(any<EpisodeListQuery>()).execute() } returns response

            val result = episodeRepository.getEpisodeList(1)

            coVerify { apolloClient.query(any<EpisodeListQuery>()).execute() }
            result shouldBe response
        }

    @Test
    fun `Verify episode detail when the episode detail fetched`() =
        coroutineTestRule.runBlockingTest {
            val response = mockk<ApolloResponse<EpisodeQuery.Data>>()
            coEvery { apolloClient.query(any<EpisodeQuery>()).execute() } returns response

            val result = episodeRepository.getEpisode("id")

            coVerify { apolloClient.query(any<EpisodeQuery>()).execute() }
            result shouldBe response
        }
}
