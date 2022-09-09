package com.esrakaya.rickandmortyepisodes.domain

import com.esrakaya.rickandmortyepisodes.EpisodeQuery
import com.esrakaya.rickandmortyepisodes.data.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeDetail @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(id: String): EpisodeQuery.Episode? {
        return episodeRepository.getEpisode(id).data?.episode
    }
}
