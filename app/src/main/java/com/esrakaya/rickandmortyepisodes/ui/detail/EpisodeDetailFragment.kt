package com.esrakaya.rickandmortyepisodes.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.esrakaya.rickandmortyepisodes.R
import com.esrakaya.rickandmortyepisodes.databinding.FragmentEpisodeDetailBinding
import com.esrakaya.rickandmortyepisodes.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private val binding by viewBinding(FragmentEpisodeDetailBinding::bind)

    private val viewModel: EpisodeDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_episode_detail, container, false)
}