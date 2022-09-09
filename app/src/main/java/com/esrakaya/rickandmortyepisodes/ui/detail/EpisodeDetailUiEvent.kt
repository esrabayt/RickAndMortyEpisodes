package com.esrakaya.rickandmortyepisodes.ui.detail

sealed class EpisodeDetailUiEvent {

    data class ShowError(val message: String? = null) : EpisodeDetailUiEvent()
}