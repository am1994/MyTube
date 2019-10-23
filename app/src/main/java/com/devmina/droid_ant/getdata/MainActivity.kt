package com.devmina.droid_ant.getdata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
           //var api_key=BuildConfig.YOUTUBE_API_KEY


        //Getting the Navigation Controller
        navController = Navigation.findNavController(this, R.id.fragment)



        //Setting the navigation controller to Bottom Nav
        bottomNav.setupWithNavController(navController)


        //Setting up the action bar
        NavigationUI.setupActionBarWithNavController(this, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

           if(destination.id == R.id.playlistVideosFragment || destination.id == R.id.videosFragment ){
               bottomNav.visibility = View.GONE


           }else{
               bottomNav.visibility = View.VISIBLE
           }


        }

    }



    //Setting Up the back button
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
}
