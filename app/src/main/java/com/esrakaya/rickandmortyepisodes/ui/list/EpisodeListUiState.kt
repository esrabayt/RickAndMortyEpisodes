package com.esrakaya.rickandmortyepisodes.ui.list

sealed class EpisodeListUiState<T>(
    val value: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : EpisodeListUiState<T>(data)
    class Error<T>(message: String?, data: T? = null) : EpisodeListUiState<T>(data, message)
    class Loading<T> : EpisodeListUiState<T>()
}