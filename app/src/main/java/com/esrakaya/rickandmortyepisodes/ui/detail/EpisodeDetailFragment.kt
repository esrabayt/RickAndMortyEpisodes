package com.esrakaya.rickandmortyepisodes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.esrakaya.rickandmortyepisodes.R
import com.esrakaya.rickandmortyepisodes.databinding.FragmentEpisodeDetailBinding
import com.esrakaya.rickandmortyepisodes.utils.collectEvent
import com.esrakaya.rickandmortyepisodes.utils.collectState
import com.esrakaya.rickandmortyepisodes.utils.showError
import com.esrakaya.rickandmortyepisodes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private val binding by viewBinding(FragmentEpisodeDetailBinding::bind)

    private val viewModel: EpisodeDetailViewModel by viewModels()

    private val args: EpisodeDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_episode_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectState(viewModel.uiState) { renderView(it) }
        collectEvent(viewModel.uiEvent) { handleEvent(it) }
        viewModel.fetch(args.id)
    }

    private fun handleEvent(uiEvent: EpisodeDetailUiEvent) {
        when (uiEvent) {
            is EpisodeDetailUiEvent.ShowError -> requireContext().showError(uiEvent.message)
        }
    }

    private fun renderView(uiState: EpisodeDetailUiState) = with(binding) {
        tvName.text = uiState.episode?.name
        tvAirDate.text = uiState.episode?.air_date
        tvEpisodeCode.text = uiState.episode?.episode
        progress.isVisible = uiState.isLoading
        clDetail.isVisible = uiState.isDetailVisible
    }
}