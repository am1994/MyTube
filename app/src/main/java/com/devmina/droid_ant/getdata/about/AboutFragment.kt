package com.devmina.droid_ant.getdata.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.devmina.droid_ant.getdata.databinding.FragmentAboutBinding

class AboutFragment: Fragment() {

    private val viewModel: AboutViewModel by lazy {
        ViewModelProviders.of(this).get(AboutViewModel::class.java)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        activity?.title = "About"

        val application= requireNotNull(activity).application

        //get argument from VideoFragment
        val desc= AboutFragmentArgs.fromBundle(arguments!!).desc

        //call VideoFragmentFactory
        val viewmodelFactory= AboutFragmentFactory(desc,application)

        //bind VideoViewModel to ViewModelFactory
        binding.viewmodel=ViewModelProviders.of(this,viewmodelFactory).get(AboutViewModel::class.java)




        return binding.root

    }
}