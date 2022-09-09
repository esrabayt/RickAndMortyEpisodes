package com.esrakaya.rickandmortyepisodes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esrakaya.rickandmortyepisodes.domain.FetchEpisodeList
import com.esrakaya.rickandmortyepisodes.ui.list.EpisodeListUiEvent.NavigateToDetail
import com.esrakaya.rickandmortyepisodes.ui.list.EpisodeListUiEvent.ShowError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val fetchEpisodeList: FetchEpisodeList
) : ViewModel() {

    private val _uiState = MutableStateFlow(EpisodeListUiState())
    val uiState: StateFlow<EpisodeListUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<EpisodeListUiEvent>()
    val uiEvent: SharedFlow<EpisodeListUiEvent> = _uiEvent.asSharedFlow()

    fun fetch() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            runCatching { fetchEpisodeList(_uiState.value.page) }
                .onSuccess {
                    val newItems = it?.results?.mapNotNull { it }.orEmpty()
                    val newPage = it?.info?.next

                    _uiState.update { state ->
                        state.copy(
                            items = state.items.orEmpty() + newItems,
                            page = newPage
                        )
                    }
                }
                .onFailure { _uiEvent.emit(ShowError(it.message)) }
                .also { _uiState.update { it.copy(isLoading = false) } }
        }
    }

    fun onItemClicked(id: String?) {
        viewModelScope.launch {
            if (id != null) {
                _uiEvent.emit(NavigateToDetail(id))
            } else {
                _uiEvent.emit(ShowError())
            }
        }
    }

    fun onRefresh() {
        _uiState.update { EpisodeListUiState() }
        fetch()
    }
}
