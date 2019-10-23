package com.devmina.droid_ant.getdata.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devmina.droid_ant.getdata.data.PlaylistData
import com.devmina.droid_ant.getdata.databinding.PlaylistItemViewBinding


class PlaylistAdapter (val onClickListener:PonClickListener): ListAdapter<PlaylistData, PlaylistAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(PlaylistItemViewBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
       holder.itemView.setOnClickListener{
            onClickListener.clickListener(item)
        }
        holder.bind( item)

    }

    class PonClickListener(val clickListener: (playlistData: PlaylistData) ->Unit){
        fun onClick(playlistData: PlaylistData)=clickListener(playlistData)
    }



    class ViewHolder (private val binding: PlaylistItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaylistData) {


            binding.playlist = item
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlaylistData>() {
        override fun areItemsTheSame(oldItem: PlaylistData, newItem: PlaylistData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlaylistData, newItem: PlaylistData): Boolean {
            return oldItem.title == newItem.title
        }
    }
}