package com.devmina.droid_ant.getdata.about

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class AboutViewModel (private val desc:String,app: Application):AndroidViewModel(app){

    private val _description = MutableLiveData<String>()

    val description: LiveData<String>
        get() = _description


    init {

        _description.value = desc
    }
}