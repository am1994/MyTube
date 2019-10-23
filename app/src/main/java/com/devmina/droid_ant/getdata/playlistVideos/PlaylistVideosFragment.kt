package com.devmina.droid_ant.getdata.playlistVideos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.getdata.databinding.FragmentPlaylistVideosBinding
import com.devmina.droid_ant.getdata.playlist.PlaylistViewModel

class PlaylistVideosFragment: Fragment(){
    /**
     * Lazily initialize our [PlaylistViewModel].
     */
    private val viewModel: PlaylistVideosViewModel by lazy {
        ViewModelProviders.of(this).get(PlaylistVideosViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application= requireNotNull(activity).application

        //activity?.title="Playlist"
        val binding= FragmentPlaylistVideosBinding.inflate(inflater)



        //get argument from PlaylistVideosFragment
        val playlistId= PlaylistVideosFragmentArgs.fromBundle(arguments!!).selectedPlaylist

        //call PlaylistVideosFragmentFactory
        val viewmodelFactory= PlaylistVideosFragmentFactory(playlistId,application,this.lifecycle,binding.youtubePlayerVideo)

        //bind PlaylistVideosViewModel to ViewModelFactory
        binding.viewmodel=ViewModelProviders.of(this,viewmodelFactory).get(PlaylistVideosViewModel::class.java)

           //viewModel.setVideoId()

        viewModel.selectedProperty.observe(this, Observer {
            if(it!=null) {
              viewModel.setVideoId()
                activity?.title=it.title
                viewModel.completedNavigation()
            }
        }
        )

          viewModel.desc.observe(this, Observer {
              if(it!= null){
                  binding.expandTextView.text = it
                  viewModel.completedBindingDesc()
              }
          })

        val adapter= PlaylistVideoAdapter(PlaylistVideoAdapter.LonClickListener{
            viewModel.displayPlaylistVideoId(it)

            viewModel.setVideoId()


        })

        binding.PlistVideos.adapter=adapter

       viewModel.videoID.observe(this, Observer {
            if(it!=null){
                viewModel.loadVideo()

               viewModel.CompletedClick()
            }

        })



        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the InformationViewModel
        binding.viewmodel=viewModel

        return binding.root
    }
}