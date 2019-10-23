package com.devmina.droid_ant.getdata.playlistVideos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devmina.droid_ant.getdata.data.PlaylistVideoData
import com.devmina.droid_ant.getdata.databinding.PlaylistVideoItemBinding

class PlaylistVideoAdapter (val onClickListener:LonClickListener): ListAdapter<PlaylistVideoData, PlaylistVideoAdapter.ViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(PlaylistVideoItemBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.itemView.setOnClickListener{view ->

            onClickListener.clickListener(item)

        }

        holder.bind( item)

    }



    class LonClickListener(val clickListener: (playlistVideoData: PlaylistVideoData) ->Unit){
        fun onClick(playlistVideoData: PlaylistVideoData)=clickListener(playlistVideoData)

    }


    class ViewHolder (private val binding: PlaylistVideoItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PlaylistVideoData) {


            binding.playlistVideo = item
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<PlaylistVideoData>() {
        override fun areItemsTheSame(oldItem: PlaylistVideoData, newItem: PlaylistVideoData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: PlaylistVideoData, newItem: PlaylistVideoData): Boolean {
            return oldItem.title == newItem.title
        }
    }
}