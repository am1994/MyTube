package com.devmina.droid_ant.getdata.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devmina.droid_ant.getdata.R
import com.devmina.droid_ant.getdata.databinding.FragmentPlaylistBinding

class PlaylistFragment:Fragment(){
    /**
     * Lazily initialize our [PlaylistViewModel].
     */
    private val viewModel:PlaylistViewModel by lazy {
        ViewModelProviders.of(this).get(PlaylistViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.title="Playlist"
        val binding=FragmentPlaylistBinding.inflate(inflater)


        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        // Giving the binding access to the InformationViewModel
        binding.viewmodel=viewModel


        val adapter= PlaylistAdapter(PlaylistAdapter.PonClickListener{
            viewModel.displayPlaylistId(it)
        })

       viewModel.navigateToPlaylistVideosFragment.observe(this, Observer {
            if(it != null && this.findNavController().currentDestination?.id== R.id.playlistFragment){

                this.findNavController().navigate(PlaylistFragmentDirections.actionPlaylistFragmentToPlaylistVideosFragment(it))
                viewModel.displayPlaylistDataCompleted()

            }
        })

        //set PlaylistAdapter in RecyclerView.adapter
        binding.PlistItem.adapter=adapter

        return binding.root
    }
}