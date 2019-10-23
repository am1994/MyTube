package com.devmina.droid_ant.getdata.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devmina.droid_ant.getdata.data.VideoData
import com.devmina.droid_ant.getdata.databinding.VideoItemViewBinding

class VideoAdapter (val onClick:onClickListener):ListAdapter<VideoData,VideoAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(VideoItemViewBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener{
          onClick.clickListener(item)
        }
        holder.bind( item)

    }

    class onClickListener(val clickListener: (videoData:VideoData) ->Unit){
       fun onClick(videoData:VideoData)=clickListener(videoData)
    }

    class ViewHolder (private val binding: VideoItemViewBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(item: VideoData) {
            binding.video = item
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<VideoData>() {
        override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return oldItem.title == newItem.title
        }
    }
}