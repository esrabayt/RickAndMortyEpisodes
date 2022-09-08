package com.esrakaya.rickandmortyepisodes.ui.list

import com.esrakaya.rickandmortyepisodes.EpisodeListQuery

data class EpisodeListUiState(
    val items: List<EpisodeListQuery.Result>? = null,
    val isLoading: Boolean = false
) {
    val isEmptyStateVisible: Boolean
        get() = items != null && items.isEmpty()
}