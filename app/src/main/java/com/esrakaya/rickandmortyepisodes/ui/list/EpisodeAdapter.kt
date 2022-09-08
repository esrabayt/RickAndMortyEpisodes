package com.esrakaya.rickandmortyepisodes.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esrakaya.rickandmortyepisodes.EpisodeListQuery
import com.esrakaya.rickandmortyepisodes.databinding.ItemEpisodeBinding
import com.esrakaya.rickandmortyepisodes.utils.inflater

class EpisodeAdapter(
    private val onItemClicked: ((String?) -> Unit)? = null
) : ListAdapter<EpisodeListQuery.Result, EpisodeAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemEpisodeBinding.inflate(
                parent.context.inflater,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(
        val binding: ItemEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EpisodeListQuery.Result) = with(binding) {
            tvName.text = item.name
            root.setOnClickListener { onItemClicked?.invoke(item.id) }
        }
    }

    companion object {
        val DIFF_CALLBACK
            get() = object : DiffUtil.ItemCallback<EpisodeListQuery.Result>() {
                override fun areItemsTheSame(
                    oldItem: EpisodeListQuery.Result,
                    newItem: EpisodeListQuery.Result
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: EpisodeListQuery.Result,
                    newItem: EpisodeListQuery.Result
                ) = oldItem == newItem
            }
    }
}
