package com.devmina.droid_ant.getdata.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.getdata.databinding.FragmentVideosBinding
import kotlinx.android.synthetic.main.fragment_videos.*



class VideosFragment:Fragment(),LifecycleObserver{

    private val viewModel:VideosViewModel by lazy{
         ViewModelProviders.of(this).get(VideosViewModel::class.java)
     }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application= requireNotNull(activity).application
        val binding=FragmentVideosBinding.inflate(inflater)

           //get argument from VideoFragment
            val videoId=VideosFragmentArgs.fromBundle(arguments!!).selectedVideo

             //call VideoFragmentFactory
            val viewmodelFactory=VideoFragmentFactory(videoId,application,this.lifecycle)

        //bind VideoViewModel to ViewModelFactory
        binding.viewmodel=ViewModelProviders.of(this,viewmodelFactory).get(VideosViewModel::class.java)

       //

        viewModel.selectedProperty.observe(this, Observer {
            if(it!=null) {
                viewModel.setVideoId(youtube_player_view)
                 activity?.title=it.title
                viewModel.completedNavigation()
            }
        }
        )



        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.setLifecycleOwner (this)


        // Giving the binding access to the InformationViewModel
        binding.viewmodel=viewModel

        //lifecycle.addObserver(youtube_player_view)
        return binding.root

    }





}
