package com.devmina.droid_ant.getdata.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.getdata.databinding.FragmentActivityBinding

class ActivityFragment:Fragment() {
    private val viewModel: ActivityViewModel by lazy {
        ViewModelProviders.of(this).get(ActivityViewModel::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.title="Activity"

        val binding= FragmentActivityBinding.inflate(inflater)

         val adapter=ActivityAdapter()

        binding.ListActivity.adapter=adapter
        binding.lifecycleOwner = this

        binding.viewmodel=viewModel

        return binding.root
    }
}