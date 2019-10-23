package com.devmina.droid_ant.getdata.about

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AboutFragmentFactory(private val desc:String,private val app:Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutViewModel::class.java)) {
            return  AboutViewModel(desc,app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}