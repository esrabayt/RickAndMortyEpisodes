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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esrakaya.rickandmortyepisodes.R
import com.esrakaya.rickandmortyepisodes.databinding.FragmentEpisodeListBinding
import com.esrakaya.rickandmortyepisodes.utils.*
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private val binding by viewBinding(FragmentEpisodeListBinding::bind)

    private val viewModel: EpisodeListViewModel by viewModels()

    private val episodeAdapter by lazy {
        EpisodeAdapter { viewModel.onItemClicked(it) }
    }

    private var pagingScrollListener: PagingScrollListener? = null

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

    override fun onDestroyView() {
        super.onDestroyView()
        pagingScrollListener = null
    }

    private fun initView() = with(binding) {
        rvEpisodes.apply {
            setPagingListener()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = episodeAdapter
        }
    }

    private fun RecyclerView.setPagingListener() {
        pagingScrollListener?.let { removeOnScrollListener(it) }
        layoutManager?.let { addOnScrollListener(it.createPagingListener()) }
    }

    private fun RecyclerView.LayoutManager.createPagingListener(): PagingScrollListener {
        return object : PagingScrollListener(this@createPagingListener as LinearLayoutManager) {
            override val lockLoadMore: Boolean
                get() = viewModel.uiState.value.lockLoadMore

            override fun loadMoreItems() {
                viewModel.fetch()
            }
        }.also(::pagingScrollListener::set)
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