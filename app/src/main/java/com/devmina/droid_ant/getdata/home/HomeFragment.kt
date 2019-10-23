package com.devmina.droid_ant.getdata.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devmina.droid_ant.getdata.R
import com.devmina.droid_ant.getdata.databinding.FragmentHomeBinding


class HomeFragment:Fragment(){

private val viewModel:HomeViewModel by lazy {
    ViewModelProviders.of(this).get(HomeViewModel::class.java)

}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        activity?.title="Home"


        val binding= FragmentHomeBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewmodel=viewModel


        val adapter=VideoAdapter(VideoAdapter.onClickListener{
            viewModel.displayVideoId(it)

        })



        viewModel.navigateToVideoFragment.observe(this, Observer {
            if(it != null && this.findNavController().currentDestination?.id== R.id.homeFragment){

                this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToVideosFragment(it))
                viewModel.displayVideoDataCompleted()

            }
        })

       binding.listItem.adapter=adapter

        return binding.root

    }
}