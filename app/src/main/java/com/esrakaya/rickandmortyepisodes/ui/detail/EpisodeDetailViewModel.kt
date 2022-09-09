package com.esrakaya.rickandmortyepisodes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrakaya.rickandmortyepisodes.domain.FetchEpisodeDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailViewModel @Inject constructor(
    private val fetchEpisodeDetail: FetchEpisodeDetail
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodeDetailUiState())
    val uiState: StateFlow<EpisodeDetailUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<EpisodeDetailUiEvent>()
    val uiEvent: SharedFlow<EpisodeDetailUiEvent> = _uiEvent.asSharedFlow()

    fun fetch(id: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching { fetchEpisodeDetail(id) }
                .onSuccess { _uiState.update { state -> state.copy(episode = it) } }
                .onFailure { _uiEvent.emit(EpisodeDetailUiEvent.ShowError(it.message)) }
                .also { _uiState.update { it.copy(isLoading = false) } }
        }
    }
}
