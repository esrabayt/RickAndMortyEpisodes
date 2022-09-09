package com.esrakaya.rickandmortyepisodes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.esrakaya.rickandmortyepisodes.R
import com.esrakaya.rickandmortyepisodes.databinding.FragmentEpisodeListBinding
import com.esrakaya.rickandmortyepisodes.utils.collectEvent
import com.esrakaya.rickandmortyepisodes.utils.collectState
import com.esrakaya.rickandmortyepisodes.utils.showError
import com.esrakaya.rickandmortyepisodes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private val binding by viewBinding(FragmentEpisodeListBinding::bind)

    private val viewModel: EpisodeListViewModel by viewModels()

    private val episodeAdapter by lazy {
        EpisodeAdapter { viewModel.onItemClicked(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_episode_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        collectState(viewModel.uiState) { renderView(it) }
        collectEvent(viewModel.uiEvent) { handleEvent(it) }
        viewModel.fetch()
    }

    private fun initView() = with(binding) {
        rvEpisodes.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = episodeAdapter
        }
    }

    private fun handleEvent(uiEvent: EpisodeListUiEvent) {
        when (uiEvent) {
            is EpisodeListUiEvent.NavigateToDetail -> {
                findNavController().navigate(
                    EpisodeListFragmentDirections.navigateToDetail(uiEvent.id)
                )
            }

            is EpisodeListUiEvent.ShowError -> requireContext().showError(uiEvent.message)
        }
    }

    private fun renderView(uiState: EpisodeListUiState) = with(binding) {
        tvEmptyState.isVisible = uiState.isEmptyStateVisible
        progress.isVisible = uiState.isLoading
        episodeAdapter.submitList(uiState.items)
    }

}