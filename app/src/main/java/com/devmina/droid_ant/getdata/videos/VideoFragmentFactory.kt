package com.devmina.droid_ant.getdata.videos

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.devmina.droid_ant.getdata.data.VideoData

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the videoId  to the ViewModel.
 */
class VideoFragmentFactory(private val videoData: VideoData,private val app:Application,private val lifecycle: Lifecycle) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideosViewModel::class.java)) {
            return  VideosViewModel(videoData,app,lifecycle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}