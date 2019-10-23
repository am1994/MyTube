package com.devmina.droid_ant.getdata.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.devmina.droid_ant.getdata.R
import com.devmina.droid_ant.getdata.YoutubeApiStatus
import com.devmina.droid_ant.getdata.databinding.FragmentMoreBinding

class MoreFragment:Fragment(){

    private val viewModel: MoreViewModel by lazy {
        ViewModelProviders.of(this).get(MoreViewModel::class.java)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        activity?.title="More"
        val binding= FragmentMoreBinding.inflate(inflater)


        viewModel.status.observe(this, Observer {
            when(it!!) {
                YoutubeApiStatus.LOADING, YoutubeApiStatus.ERROR -> {
                    binding.txTitle.visibility = View.GONE
                    binding.txSub.visibility = View.GONE
                    binding.txViews.visibility = View.GONE
                    binding.imgAbout.visibility = View.GONE
                    binding.txAbout.visibility = View.GONE
                    binding.txCont.visibility = View.GONE
                    binding.imgFac.visibility = View.GONE
                    binding.imgInsta.visibility = View.GONE
                    binding.imgTwit.visibility = View.GONE
                    binding.imgGoog.visibility = View.GONE
                }
                YoutubeApiStatus.DONE -> {
                    binding.txTitle.visibility = View.VISIBLE
                    binding.txSub.visibility = View.VISIBLE
                    binding.txViews.visibility = View.VISIBLE
                    binding.imgAbout.visibility = View.VISIBLE
                    binding.txAbout.visibility = View.VISIBLE
                    binding.txCont.visibility = View.VISIBLE
                    binding.imgFac.visibility = View.VISIBLE
                    binding.imgInsta.visibility = View.VISIBLE
                    binding.imgTwit.visibility = View.VISIBLE
                    binding.imgGoog.visibility = View.VISIBLE
                }
            }
        })
        binding.txAbout.setOnClickListener{ View ->
            viewModel.DesplayData()
        }
        viewModel.navigateToAboutFragment.observe(this, Observer {

                if(it != null && this.findNavController().currentDestination?.id== R.id.moreFragment){
                    this.findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToAboutFragment(it))
                    viewModel.CompletedDesplayData()

                }

        })

           binding.imgFac.setOnClickListener {

               val url = "http://www.facebook.com"
               val i = Intent(Intent.ACTION_VIEW)
               i.data = Uri.parse(url)
               startActivity(i)
           }


        binding.imgInsta.setOnClickListener {

            val url = "http://www.instagram.com"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.imgTwit.setOnClickListener {

            val url = "http://www.twitter.com"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.imgGoog.setOnClickListener {

            val url = "http://www.google.com"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        binding.lifecycleOwner = this

        binding.viewmodel=viewModel

       return binding.root
}

}