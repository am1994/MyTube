package com.devmina.droid_ant.getdata.network

import com.devmina.droid_ant.getdata.BuildConfig
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.extensions.android.json.AndroidJsonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.*


object Network{

    val APP_NAME="getData"
    var api_key= BuildConfig.YOUTUBE_API_KEY

    var YoutubeDataEndPoint:YouTube=buildYouTubeEndpoint()

    var Channelid="[your_channel_id]"

    private fun buildYouTubeEndpoint(): YouTube {
        return YouTube.Builder(AndroidHttp.newCompatibleTransport(), AndroidJsonFactory(), null)
            .setApplicationName(APP_NAME)
            .build()
    }

    /**
     * get Video Data
     */
      fun  getVideoData(VideoId:String):VideoListResponse {

         return  YoutubeDataEndPoint
            .videos()
            .list("snippet")
            .setFields("items(snippet(publishedAt,channelId,title,description,thumbnails(default(url)),channelTitle))")
            .setId(VideoId)
            .setKey(api_key)
             .execute()


    }

    /**
     * get Channel Data
     */

    fun getChannelDataFromtheServer():ChannelListResponse{


        return  YoutubeDataEndPoint
            .channels()
            .list("snippet,statistics")
            .setFields("items(snippet(title,description,thumbnails(medium(url))),statistics(viewCount,subscriberCount))")
            .setId(Channelid)
            .setKey(api_key)
            .execute()

    }

    /**
     * get Videos' Playlist from the server
     */

    fun getVideosPlaylist(PlaylistID:String):PlaylistItemListResponse{
        return YoutubeDataEndPoint
            .PlaylistItems()
            .list("snippet")
            .setMaxResults(50)
            .setPlaylistId(PlaylistID)
            .setFields("items(snippet(publishedAt,title,description,thumbnails(default(url)),resourceId(videoId)))")
            .setKey(api_key)
            .execute()
    }
    /**
     * get Channel's Videos Data  by search (Home Fragment)
     */

    fun getVideoDataBySearch():SearchListResponse{

        return YoutubeDataEndPoint
            .search()
            .list("snippet")
            .setMaxResults(50)
            .setChannelId(Channelid)
            .setFields("items(id(kind,videoId),snippet(title,description,thumbnails(medium(url))))")
            .setKey(api_key)
            .execute()
    }

    /**
     *  get Playlist Response
     *  */

    fun getPlaylistList():PlaylistListResponse{
         return YoutubeDataEndPoint
             .Playlists()
             .list("snippet")
             .setMaxResults(50)
             .setChannelId(Channelid)
             .setFields("items(id,snippet(title,description,thumbnails(medium(url))))")
             .setKey(api_key)
             .execute()

    }
    /**
     * get Activities
     */
    fun getActivtiesList():ActivityListResponse{
        return YoutubeDataEndPoint
            .Activities()
            .list("snippet")
            .setMaxResults(50)
            .setChannelId(Channelid)
            .setFields("items(snippet(publishedAt,title,description,thumbnails(medium(url))))")
            .setKey(api_key)
            .execute()

    }

}