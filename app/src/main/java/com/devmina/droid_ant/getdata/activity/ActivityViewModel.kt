package com.devmina.droid_ant.getdata.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devmina.droid_ant.getdata.UpdateDate
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.data.ActivityData
import com.devmina.droid_ant.getdata.network.Network.getActivtiesList
import com.google.api.services.youtube.model.ActivityListResponse
import kotlinx.coroutines.*
import timber.log.Timber

class ActivityViewModel :ViewModel(){

    private var _listActivityData=MutableLiveData<MutableList<ActivityData>>()
    val listActivityData:LiveData<MutableList<ActivityData>>
        get() = _listActivityData

    private val _Activitystatus = MutableLiveData<YoutubeApiStatus>()
    val Activitystatus: LiveData<YoutubeApiStatus>
        get() = _Activitystatus




    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModeljob= Job()
    // the Coroutine runs using the Main (UI) dispatcher
    private var couritineScope= CoroutineScope(viewModeljob)

    init {
        couritineScope.launch {
            try{
                _Activitystatus.postValue(YoutubeApiStatus.LOADING)
                Update()
                _Activitystatus.postValue(YoutubeApiStatus.DONE)
            }catch (e: Throwable){
                _Activitystatus.postValue(YoutubeApiStatus.ERROR)
               Timber.e(e.message.toString())

            }
        }

    }
    private suspend fun Update()  {

        val listActivity: ActivityListResponse = withContext(Dispatchers.IO){

            getActivtiesList()
        }

        val list:MutableList<ActivityData> = mutableListOf()


       for (activity in listActivity.items) {
                list.add(ActivityData(UpdateDate(activity.snippet.publishedAt.toString()),activity.snippet.title,activity.snippet.description,activity.snippet.thumbnails.medium.url))
        }

        _listActivityData.postValue(list)

    }


}