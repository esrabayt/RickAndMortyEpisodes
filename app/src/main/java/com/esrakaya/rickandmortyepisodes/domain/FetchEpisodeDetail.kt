package com.esrakaya.rickandmortyepisodes.domain

import com.apollographql.apollo3.api.ApolloResponse
import com.esrakaya.rickandmortyepisodes.EpisodeQuery
import com.esrakaya.rickandmortyepisodes.data.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeDetail @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(id: String): ApolloResponse<EpisodeQuery.Data> {
        return episodeRepository.getEpisode(id)
    }
}
