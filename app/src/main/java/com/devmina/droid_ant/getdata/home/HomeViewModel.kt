package com.devmina.droid_ant.getdata.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.data.VideoData
import com.devmina.droid_ant.getdata.network.Network.getVideoDataBySearch
import com.google.api.services.youtube.model.SearchListResponse
import kotlinx.coroutines.*
import timber.log.Timber


class HomeViewModel : ViewModel(){


    private var _listData=MutableLiveData<MutableList<VideoData>>()
    val listData:LiveData<MutableList<VideoData>>
        get() = _listData

    private val _navigateToVideoFragment = MutableLiveData<VideoData>()
    val navigateToVideoFragment:LiveData<VideoData>
        get() = _navigateToVideoFragment

    private val _status = MutableLiveData<YoutubeApiStatus>()
    val status: LiveData<YoutubeApiStatus>
        get() = _status




    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob= Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private var couritineScope= CoroutineScope(viewModeljob)

    init {
         couritineScope.launch {
             try{
                _status.postValue(YoutubeApiStatus.LOADING)
             update()
                _status.postValue(YoutubeApiStatus.DONE)
         }catch (e: Throwable){
              _status.postValue(YoutubeApiStatus.ERROR)
                 Timber.e(e.message.toString())

             }
         }

    }


   private suspend fun update()  {

        val listVideo: SearchListResponse = withContext(Dispatchers.IO){

            getVideoDataBySearch()
        }

        val list:MutableList<VideoData> = mutableListOf()


       for (video in listVideo.items) {
           if(video.id.kind=="youtube#video")
           list.add(VideoData(video.id.videoId,video.snippet.title,video.snippet.thumbnails.medium.url))
       }

      _listData.postValue(list)

    }


    fun displayVideoId(videoData: VideoData){
          _navigateToVideoFragment.postValue(videoData)
    }

     fun displayVideoDataCompleted(){
         _navigateToVideoFragment.postValue(null)
     }

    override fun onCleared() {
        super.onCleared()
        viewModeljob.cancel()
    }



}


