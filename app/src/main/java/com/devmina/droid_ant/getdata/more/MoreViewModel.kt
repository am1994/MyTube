package com.devmina.droid_ant.getdata.more

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmina.droid_ant.getdata.UpdateNumber
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.network.Network.getChannelDataFromtheServer
import com.google.api.services.youtube.model.ChannelListResponse
import kotlinx.coroutines.*

class MoreViewModel : ViewModel(){


    private val _status = MutableLiveData<YoutubeApiStatus>()
    val status: LiveData<YoutubeApiStatus>
        get() = _status


    private val _title=MutableLiveData<String>()
    val title:LiveData<String>
    get() = _title

    private val _thumbnail=MutableLiveData<String>()
    val thumbnail:LiveData<String>
        get() = _thumbnail

    private val _total_Subscriber=MutableLiveData<String>()
    val totalSubscriber:LiveData<String>
    get() = _total_Subscriber

    private val _total_Views=MutableLiveData<String>()
    val totalViews:LiveData<String>
        get() = _total_Views

    private val _description=MutableLiveData<String>()
    val description:LiveData<String>
    get() = _description

    private val _navigateToAboutFragment=MutableLiveData<String>()
    val navigateToAboutFragment:LiveData<String>
    get() = _navigateToAboutFragment

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob= Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private var couritineScope= CoroutineScope(viewModeljob)

    init {
        couritineScope.launch {
            try{
                _status.postValue(YoutubeApiStatus.LOADING)
                Update()
                _status.postValue(YoutubeApiStatus.DONE)
            }catch (e: Throwable){
                _status.postValue(YoutubeApiStatus.ERROR)
                Log.e("More",e.message.toString())

            }
        }

    }


    private suspend fun Update()  {

        val channelData: ChannelListResponse = withContext(Dispatchers.IO){

            getChannelDataFromtheServer()
        }


        _title.postValue(channelData.items[0].snippet.title)
        _thumbnail.postValue(channelData.items[0].snippet.thumbnails.medium.url)
        _description.postValue(channelData.items[0].snippet.description)
        val subs=channelData.items[0].statistics.subscriberCount.toInt()
        _total_Subscriber.postValue(UpdateNumber(subs))
        val count=channelData.items[0].statistics.viewCount.toInt()
        _total_Views.postValue(UpdateNumber(count))


    }


    fun DesplayData(){
        _navigateToAboutFragment.postValue(_description.value)
    }

    fun CompletedDesplayData(){
        _navigateToAboutFragment.postValue(null)
    }

    override fun onCleared() {
        super.onCleared()
        viewModeljob.cancel()
    }


    companion object {
        private const val Tag="MoreViewModel"
    }

}