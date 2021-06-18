package com.osamaaltawil.alquds

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.MediaController
import android.widget.VideoView
import com.osamaaltawil.alquds.models.video_model
import com.yausername.youtubedl_android.YoutubeDL
import com.yausername.youtubedl_android.YoutubeDLRequest
import kotlinx.android.synthetic.main.activity_video_details_.*

class Video_details_Activity : AppCompatActivity() {
    var new_video_url=""
    var VideoView:VideoView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details_)


        supportActionBar!!.title=resources.getString(R.string.title_details)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //show alert dialog
        val dialog=Custom_AlertDialog(this)
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))
        dialog.show_dialog()

        ///get and set data in view...........
        val data =intent.getSerializableExtra("data") as video_model

        //text view title
        _vp_tv_title.textSize=18f
        _vp_tv_title.maxLines=2
        _vp_tv_title.text=data.title

        //text view description
        _vp_tv_description.text=data.description

        //Video view
         VideoView=findViewById<VideoView>(R.id.video_view)





         // if the data source from youtube -> get download url .mp4 using -> You tube video url
        if (data.video_source == "Youtube") {
            val thread = Thread {
                run {
                    try {

                        YoutubeDL.getInstance().init(this)

                        val request = YoutubeDLRequest(data.video_url)
                        request.addOption("-f", "best")
                        val streamInfo = YoutubeDL.getInstance()
                        val i = streamInfo.getInfo(request)
                        new_video_url = i.url.toString()
                        dialog.dismiss_dialog()
                        Log.e("URL YOUTUBE", i.url.toString())


                    } catch (e: InterruptedException) {

                    }

                    runOnUiThread(Runnable() {

                        run() {
                            run_videoPlayer(new_video_url)
                        }
                    })
                }
            }
            thread.start();

        } else if (data.video_source == "Local.MP4") {
            run_videoPlayer(data.video_url)
            dialog.dismiss_dialog()

        }






    }

    fun run_videoPlayer(url: String) {

         progressBar.visibility=View.VISIBLE

        val onInfoToPlayStateListener=object : MediaPlayer.OnInfoListener{
            override fun onInfo(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
                if (MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START == p1) {
                    progressBar.visibility = View.GONE
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_START == p1) {
                    progressBar.visibility = View.VISIBLE
                }
                if (MediaPlayer.MEDIA_INFO_BUFFERING_END == p1) {
                    progressBar.visibility = View.GONE
                }

                return false
            }
        }

        val mediaController = MediaController(this)
        mediaController.setAnchorView(video_view)

        video_view.setVideoPath(url)
        video_view.setMediaController(mediaController)
        video_view.requestFocus()
        video_view.setOnInfoListener(onInfoToPlayStateListener)
        video_view.start()


    }





    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}