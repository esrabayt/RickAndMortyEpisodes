package com.esrakaya.rickandmortyepisodes.ui.detail

import com.esrakaya.rickandmortyepisodes.EpisodeQuery

data class EpisodeDetailUiState(
    val episode: EpisodeQuery.Episode? = null,
    val isLoading: Boolean = false
) {
    val isDetailVisible: Boolean
        get() = episode != null
}