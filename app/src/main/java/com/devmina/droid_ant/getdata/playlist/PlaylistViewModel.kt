package com.devmina.droid_ant.getdata.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.data.PlaylistData
import com.devmina.droid_ant.getdata.network.Network.getPlaylistList
import com.google.api.services.youtube.model.PlaylistListResponse
import kotlinx.coroutines.*
import timber.log.Timber


class PlaylistViewModel: ViewModel(){

    private var _listPlaylistData=MutableLiveData<MutableList<PlaylistData>>()
    val listPlaylistData:LiveData<MutableList<PlaylistData>>
        get() = _listPlaylistData

    private val _navigateToPlaylistVideosFragment = MutableLiveData<PlaylistData>()
    val navigateToPlaylistVideosFragment:LiveData<PlaylistData>
        get() = _navigateToPlaylistVideosFragment



    private var _playlistStatus=MutableLiveData<YoutubeApiStatus>()
    val playlistStatus:LiveData<YoutubeApiStatus>
    get() = _playlistStatus



    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob= Job()
    // the Coroutine runs using the Main (UI) dispatcher
private var couritineScope=CoroutineScope(viewModeljob)



    init {

        couritineScope.launch {
            try{
                _playlistStatus.postValue(YoutubeApiStatus.LOADING)
                Update()
                _playlistStatus.postValue(YoutubeApiStatus.DONE)
            }catch (e: Throwable){

                _playlistStatus.postValue(YoutubeApiStatus.ERROR)
                Timber.e(e.message.toString())


            }
        }
    }





    private suspend fun Update(){

                val playlists: PlaylistListResponse = withContext(Dispatchers.IO){
                                getPlaylistList()
                    }

        val list:MutableList<PlaylistData> = mutableListOf()


        for (playlist in playlists.items) {
                list.add(PlaylistData(playlist.id,playlist.snippet.title,playlist.snippet.thumbnails.medium.url))
        }

        _listPlaylistData.postValue(list)

         }

    fun displayPlaylistId(playlistData: PlaylistData){
        _navigateToPlaylistVideosFragment.postValue(playlistData)
    }

    fun displayPlaylistDataCompleted(){
        _navigateToPlaylistVideosFragment.postValue(null)
    }
    override fun onCleared() {
        super.onCleared()
        viewModeljob.cancel()
    }



    }



