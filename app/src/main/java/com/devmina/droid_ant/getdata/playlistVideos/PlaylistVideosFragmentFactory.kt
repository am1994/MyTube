package com.devmina.droid_ant.getdata.playlistVideos

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devmina.droid_ant.getdata.data.PlaylistData

class PlaylistVideosFragmentFactory(private val playlistData: PlaylistData, private val app: Application, private val lifecycle: Lifecycle,private val youTubePlayerView: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaylistVideosViewModel::class.java)) {
            return  PlaylistVideosViewModel(playlistData,app,lifecycle,youTubePlayerView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}