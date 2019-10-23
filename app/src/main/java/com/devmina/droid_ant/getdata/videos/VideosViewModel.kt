package com.devmina.droid_ant.getdata.videos

import android.app.Application
import androidx.lifecycle.*
import com.devmina.droid_ant.getdata.UpdateDate
import com.devmina.droid_ant.getdata.data.VideoData
import com.devmina.droid_ant.getdata.network.Network.getVideoData
import com.google.api.services.youtube.model.VideoListResponse
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.*

class VideosViewModel (private val videoData:VideoData,app:Application,val lifecycle:Lifecycle):AndroidViewModel(app),LifecycleObserver{

    private val _selectedProperty = MutableLiveData<VideoData>()

    val selectedProperty: LiveData<VideoData>
        get() = _selectedProperty

    private var _status=MutableLiveData<String>()
    val status:LiveData<String>
        get() = _status

    private var _title=MutableLiveData<String>()
    val title:LiveData<String>
        get() = _title

    private var _date=MutableLiveData<String>()
    val date:LiveData<String>
        get() = _date

    private var _desc=MutableLiveData<String>()
    val desc:LiveData<String>
        get() = _desc





    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob= Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private var couritineScope= CoroutineScope(viewModeljob)



    init {

        _selectedProperty.value = videoData

        couritineScope.launch {
            try {
                Update()
            }catch (e:Exception){
                _status.postValue("failure")
            }
        }
    }


     fun setVideoId(youtubePlayerView: YouTubePlayerView){
         lifecycle.addObserver(youtubePlayerView)

         youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = videoData.videoId
                youTubePlayer.loadVideo(videoId, 0f)

            }
        })

    }


          private suspend fun Update(){

              val videoResponse: VideoListResponse = withContext(Dispatchers.IO){
                  getVideoData(videoData.videoId)
              }

                  val date=videoResponse.items[0].snippet.publishedAt
                  val title=videoResponse.items[0].snippet.title
                  val description=videoResponse.items[0].snippet.description
                  val channelTitle=videoResponse.items[0].snippet.channelTitle
                  val thumbnail=videoResponse.items[0].snippet.thumbnails

                        /* val DateT:String="$date"
                         var newDate:String=""
                         var i:Int=0
                           do{
                               newDate+=DateT[i]
                               i++
                           }while (i<10)*/


                     _title.postValue(title)
                    _date.postValue(UpdateDate("$date"))
                    _desc.postValue(description)





          }

       fun completedNavigation(){
           _selectedProperty.postValue(null)

       }

      fun WriteComment(){
          //TODO : write a comment
      }
      fun likedVideo(){
        //TODO : like a video
      }

    override fun onCleared() {
        super.onCleared()
        viewModeljob.cancel()
    }
}