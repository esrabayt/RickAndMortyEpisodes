package com.esrakaya.rickandmortyepisodes.ui.list

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esrakaya.rickandmortyepisodes.databinding.ItemEpisodeBinding
import com.esrakaya.rickandmortyepisodes.utils.inflater

class EpisodeAdapter : ListAdapter<EpisodeViewData, EpisodeAdapter.ViewHolder>(
    EpisodeViewData.DIFF_CALLBACK
) {

    var onItemClicked: ((EpisodeViewData) -> Unit)? = null

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
        holder.binding.root.setOnClickListener { onItemClicked?.invoke(getItem(position)) }
    }

    inner class ViewHolder(
        val binding: ItemEpisodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: EpisodeViewData) = with(binding) {
            tvName.text = item.name
        }
    }
}

data class EpisodeViewData(
    val id: Int,
    val name: String
) {

    companion object {
        val DIFF_CALLBACK
            get() = object : DiffUtil.ItemCallback<EpisodeViewData>() {
                override fun areItemsTheSame(
                    oldItem: EpisodeViewData,
                    newItem: EpisodeViewData
                ) = oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: EpisodeViewData,
                    newItem: EpisodeViewData
                ) = oldItem == newItem
            }
    }
}
