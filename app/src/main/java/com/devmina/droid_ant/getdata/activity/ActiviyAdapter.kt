package com.devmina.droid_ant.getdata.activity


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devmina.droid_ant.getdata.data.ActivityData
import com.devmina.droid_ant.getdata.databinding.ActivityItemViewBinding


class ActivityAdapter (): ListAdapter<ActivityData, ActivityAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return  ViewHolder(ActivityItemViewBinding.inflate(LayoutInflater.from(parent.context)))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind( item)

    }


    class ViewHolder (private val binding: ActivityItemViewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ActivityData) {

            binding.activity = item
            binding.executePendingBindings()
        }


    }

    companion object DiffCallback : DiffUtil.ItemCallback<ActivityData>() {
        override fun areItemsTheSame(oldItem: ActivityData, newItem: ActivityData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ActivityData, newItem: ActivityData): Boolean {
            return oldItem.title == newItem.title
        }
    }
}