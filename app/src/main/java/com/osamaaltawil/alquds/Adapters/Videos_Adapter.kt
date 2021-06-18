package com.osamaaltawil.alquds.Adapters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osamaaltawil.alquds.R
import com.osamaaltawil.alquds.Video_details_Activity
import com.osamaaltawil.alquds.models.video_model
import kotlinx.android.synthetic.main.rc_video.view.*
import java.lang.RuntimeException
import java.util.regex.Pattern

class Videos_Adapter(var activity: Activity, var data: ArrayList<video_model>) :
    RecyclerView.Adapter<Videos_Adapter.MyViewHolder>() {

    var inUrl=""

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val click = itemView.vp_layout
        val img = itemView.vp_image
        val title = itemView.tv_vp_title
        val description = itemView.tv_vp_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view=LayoutInflater.from(activity).inflate(R.layout.rc_video,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.title.setText(data[position].title)
        holder.description.setText(data[position].description)

        //load video image
        //if youtube -->convert video url to get image url
        //if Local get image from Firebase
        if (data[position].video_source=="Youtube"){
            val img_url=Uri.parse("https://img.youtube.com/vi/"+extractYoutubeId(data[position].video_url)+"/0.jpg")
            Glide.with(activity).load(img_url).into(holder.img)
            Log.e("img",img_url.toString())

       }
        else if (data[position].video_source=="Local.MP4"){
            Glide.with(activity).load(data[position].img_url).into(holder.img)

        }


        //send data using Serializable
        holder.click.setOnClickListener {
            val i = Intent(activity,Video_details_Activity::class.java)
            i.putExtra("data",video_model(
                data[position].title,
                data[position].description,
                data[position].video_url,
                data[position].video_source,
                data[position].img_url
            ))
            activity.startActivity(i)
        }
    }


    override fun getItemCount(): Int {
        return data.size
    }

    fun extractYoutubeId(url: String): String {
        //get youtube video id to get image cover video..
        try {
            inUrl = url.replace("&feature=youtu.be", "")
            if (inUrl.toLowerCase().contains("youtu.be")) {
                return inUrl.substring(inUrl.lastIndexOf("/") + 1);
            }
            val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
            val compiledPattern = Pattern.compile(pattern)
            val matcher = compiledPattern.matcher(inUrl)
            if (matcher.find()) {
                return matcher.group();
            }

        }catch (e:RuntimeException){
            Log.e("TAG",e.message.toString())
        }


        return ""

    }

}
