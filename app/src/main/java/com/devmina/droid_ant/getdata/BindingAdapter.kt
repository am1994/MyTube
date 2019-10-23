package com.devmina.droid_ant.getdata

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.devmina.droid_ant.getdata.activity.ActivityAdapter
import com.devmina.droid_ant.getdata.data.ActivityData
import com.devmina.droid_ant.getdata.data.PlaylistData
import com.devmina.droid_ant.getdata.data.PlaylistVideoData
import com.devmina.droid_ant.getdata.data.VideoData
import com.devmina.droid_ant.getdata.home.VideoAdapter
import com.devmina.droid_ant.getdata.playlist.PlaylistAdapter
import com.devmina.droid_ant.getdata.playlistVideos.PlaylistVideoAdapter


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image
                        )
                   )
            .into(imgView)
    }
}
/**
 * Uses the Glide library to load a circle image by URL into an [ImageView]
 */
@BindingAdapter("circleImageUrl")
fun bindCircleImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .circleCrop()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}
/**
 * submit list of videos in recyclerView
 */
@BindingAdapter("listVideoData")
fun bindVideoRecyclerView(recyclerView: RecyclerView,data: List<VideoData>?){
    val adapter =  recyclerView.adapter as VideoAdapter
    adapter.submitList(data)
}

/**
 * check out the youtube api status
 */

@BindingAdapter("youtubeApiStatus")
fun bindStatus(statusImageView: ImageView, status: YoutubeApiStatus?) {
    when (status) {
        YoutubeApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        YoutubeApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.error_connection)
        }
        YoutubeApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }

}

/**
 * submit list of playlist in recyclerView
 */
@BindingAdapter("listPlaylistData")
fun bindPlaylistRecyclerView(recyclerView: RecyclerView,data: List<PlaylistData>?){
    val adapter =  recyclerView.adapter as PlaylistAdapter
    adapter.submitList(data)
}

/**
 * submit playlist videos in recyclerView
 */
@BindingAdapter("listPlaylistVideoData")
fun bindPlaylistVideoRecyclerView(recyclerView: RecyclerView,data: List<PlaylistVideoData>?){
    val adapter=recyclerView.adapter as PlaylistVideoAdapter
    adapter.submitList(data)
}

/**
 * submit list of activity in recyclerView
 */
@BindingAdapter("listActivityData")
fun bindAcivityRecyclerView(recyclerView: RecyclerView,data: List<ActivityData>?){
    val adapter=recyclerView.adapter as ActivityAdapter
    adapter.submitList(data)
}


