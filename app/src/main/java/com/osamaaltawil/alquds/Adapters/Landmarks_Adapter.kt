package com.osamaaltawil.alquds.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osamaaltawil.alquds.Post_Details_Activity
import com.osamaaltawil.alquds.R
import com.osamaaltawil.alquds.models.L_I_model
import kotlinx.android.synthetic.main.rc_landmarks_item.view.*

class Landmarks_Adapter(var activity: Activity, var data: ArrayList<L_I_model>) :
    RecyclerView.Adapter<Landmarks_Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val click = itemView.landmarkItem
        val img = itemView.img_landmark_item
        val title = itemView.tv_landmark_title
        val description = itemView.tv_landmark_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view=LayoutInflater.from(activity).inflate(R.layout.rc_landmarks_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(activity).load(data[position].img_array[0]).into(holder.img)
        holder.title.setText(data[position].name)
        holder.description.setText(data[position].description)
        //send data using Serializable
        holder.click.setOnClickListener {
            val i = Intent(activity,Post_Details_Activity::class.java)
            i.putExtra("data",L_I_model(
                data[position].name,
                data[position].img_array,
                data[position].description
            ))
            activity.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}
