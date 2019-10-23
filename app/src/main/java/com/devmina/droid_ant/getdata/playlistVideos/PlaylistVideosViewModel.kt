package com.devmina.droid_ant.getdata.playlistVideos

import android.app.Application
import androidx.lifecycle.*
import com.devmina.droid_ant.getdata.UpdateDate
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.data.PlaylistData
import com.devmina.droid_ant.getdata.data.PlaylistVideoData
import com.devmina.droid_ant.getdata.network.Network.getVideosPlaylist
import com.google.api.services.youtube.model.PlaylistItemListResponse
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.loadOrCueVideo
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.coroutines.*
import timber.log.Timber


class PlaylistVideosViewModel (private val playlistData: PlaylistData, app: Application,private val lifecycle: Lifecycle,private val youtubePlayerView: YouTubePlayerView):AndroidViewModel(app), LifecycleObserver {

    private val _selectedProperty = MutableLiveData<PlaylistData>()

    val selectedProperty: LiveData<PlaylistData>
        get() = _selectedProperty

    private var _status = MutableLiveData<String>()
    val status: LiveData<String>
        get() = _status

    private var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title

    private var _thumbnail = MutableLiveData<String>()
    val thumbnail: LiveData<String>
        get() = _thumbnail

    private var _date = MutableLiveData<String>()
    val date: LiveData<String>
        get() = _date


    private var _desc = MutableLiveData<String>()
    val desc: LiveData<String>
        get() = _desc

    private var _listPlaylistVideoData = MutableLiveData<MutableList<PlaylistVideoData>>()
    val listPlayListVideoData: LiveData<MutableList<PlaylistVideoData>>
        get() = _listPlaylistVideoData

    private var _videoID=MutableLiveData<String>()
    val videoID:LiveData<String>
       get() = _videoID




    private var _playlistVideoStatus=MutableLiveData<YoutubeApiStatus>()
    val playlistVideoStatus:LiveData<YoutubeApiStatus>
        get() = _playlistVideoStatus

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob = Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private var couritineScope = CoroutineScope(viewModeljob)

    // Create a Coroutine scope using a
   var Start_id=""
 var youtubePlayer2:YouTubePlayer?=null
    init {

        //TODO search for all video randomly
        _selectedProperty.value = playlistData
        couritineScope.launch {
            try {
                _playlistVideoStatus.postValue(YoutubeApiStatus.LOADING)
                Update()
                _playlistVideoStatus.postValue(YoutubeApiStatus.DONE)




            } catch (e: Exception) {
                _playlistVideoStatus.postValue(YoutubeApiStatus.ERROR)
                Timber.e(e.message.toString())

            }
        }


    }

    fun displayPlaylistVideoId(playlistVideoData: PlaylistVideoData){
        _videoID.postValue(playlistVideoData.videoId)
        _desc.postValue(playlistVideoData.description)
        _title.postValue(playlistVideoData.title)
        _date.postValue(playlistVideoData.publishedAt)


    }
   fun CompletedClick(){
        _videoID.postValue(null)

    }


   fun loadVideo(){
       try {


           youtubePlayer2!!.loadOrCueVideo(
               lifecycle,
               _videoID.value.toString(),
               0f
           )
           Thread.sleep(1000)
       } catch (exception: InterruptedException) {
           exception.printStackTrace()
       }

    }

    fun setVideoId(){
        lifecycle.addObserver(youtubePlayerView)


        youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer:YouTubePlayer) {
                youTubePlayer.loadOrCueVideo(

                    lifecycle,
                    Start_id,
                    0f
                )


                           youtubePlayer2=youTubePlayer
                       }





        })
    }





    private suspend fun Update() {

         val playlistResponse: PlaylistItemListResponse=withContext(Dispatchers.IO) {

            getVideosPlaylist(playlistData.id)
        }

        val title = playlistResponse.items[0].snippet.title
        val description = playlistResponse.items[0].snippet.description
        // val channelTitle=videoResponse.items[0].snippet.channelTitle
        val thumbnail = playlistResponse.items[0].snippet.thumbnails.default.url
        val date = playlistResponse.items[0].snippet.publishedAt

        Start_id=playlistResponse.items[0].snippet.resourceId.videoId
        //_videoID.postValue(playlistResponse.items[0].snippet.resourceId.videoId)

        _title.postValue(title)
        _desc.postValue(description)
        _date.postValue(UpdateDate("$date"))


        val list: MutableList<PlaylistVideoData> = mutableListOf()


        for (item in playlistResponse.items) {
            val D = item.snippet.publishedAt
            list.add(
                PlaylistVideoData(
                    item.snippet.resourceId.videoId,
                    item.snippet.title,
                    item.snippet.description,
                    item.snippet.thumbnails.default.url,
                    UpdateDate("$D")
                )
            )
        }

         _listPlaylistVideoData.postValue(list)



    }






    fun completedNavigation(){
        _selectedProperty.postValue(null)

    }

    fun completedBindingDesc(){
        _desc.postValue(null)
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






    companion object{
        val Tag="PlaylistVideosViewModel"
    }
}

