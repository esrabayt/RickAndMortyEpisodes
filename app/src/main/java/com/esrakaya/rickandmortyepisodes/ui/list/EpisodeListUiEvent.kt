package com.esrakaya.rickandmortyepisodes.ui.list

sealed class EpisodeListUiEvent {

    data class ShowError(val message: String? = null) : EpisodeListUiEvent()

    data class NavigateToDetail(val id: String) : EpisodeListUiEvent()
}