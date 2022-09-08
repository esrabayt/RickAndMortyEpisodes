package com.esrakaya.rickandmortyepisodes.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.esrakaya.rickandmortyepisodes.R
import com.esrakaya.rickandmortyepisodes.databinding.FragmentEpisodeListBinding
import com.esrakaya.rickandmortyepisodes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private val binding by viewBinding(FragmentEpisodeListBinding::bind)

    private val viewModel: EpisodeListViewModel by viewModels()

    private val episodeAdapter by lazy { EpisodeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_episode_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun initView() = with(binding) {
        rvEpisodes.adapter = episodeAdapter

        episodeAdapter.onItemClicked = { character ->
            character.let {
                findNavController().navigate(
                    EpisodeListFragmentDirections.navigateToDetail("0")
                )
            }
        }
    }

    private fun observeLiveData() {
        val list = listOf(
            EpisodeViewData(0, "test1"),
            EpisodeViewData(1, "test2")
        )

        episodeAdapter.submitList(list)
    }
}