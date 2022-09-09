package com.esrakaya.rickandmortyepisodes.domain

import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.data.EpisodeRepository
import javax.inject.Inject

class FetchEpisodeList @Inject constructor(
    private val episodeRepository: EpisodeRepository
) {

    suspend operator fun invoke(page: Int?): EpisodeListQuery.Episodes? {
        return episodeRepository.getEpisodeList(page).data?.episodes
    }
}
