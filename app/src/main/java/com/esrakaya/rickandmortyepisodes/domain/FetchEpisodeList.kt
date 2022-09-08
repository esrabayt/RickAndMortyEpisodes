package com.esrakaya.rickandmortyepisodes.domain

import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.data.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeList @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(): List<EpisodeListQuery.Result> {
        return episodeRepository.getEpisodeList().data?.episodes?.results
            ?.mapNotNull { it }
            .orEmpty()
    }
}
