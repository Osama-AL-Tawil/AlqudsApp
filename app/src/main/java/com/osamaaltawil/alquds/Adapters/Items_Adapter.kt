package com.osamaaltawil.alquds.Adapters

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osamaaltawil.alquds.*
import com.osamaaltawil.alquds.models.data_model
import kotlinx.android.synthetic.main.rc_items.view.*

class Items_Adapter(var activity: Activity, var data: ArrayList<data_model>) :
    RecyclerView.Adapter<Items_Adapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val click = itemView.layout_landmark
        val img = itemView.img_landmark
        val title = itemView.tv_landmark
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view=LayoutInflater.from(activity).inflate(R.layout.rc_items,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(activity).load(data[position].img).into(holder.img)
        holder.title.setText(data[position].title)

        holder.click.layoutParams.width=Function(activity).calculateWidth()

            holder.click.setOnClickListener {

                if (data[position].click == "Landmarks") {

                    val i = Intent(activity, LandmarkDetails_Activity::class.java)
                    i.putExtra("title", data[position].title)
                    activity.startActivity(i)

                } else if (data[position].click == "General information") {

                    val i = Intent(activity,GInformations_list_Activity::class.java)
                    i.putExtra("title", data[position].title)
                    activity.startActivity(i)

                } else if (data[position].click == "Video Categories") {

                    val i = Intent(activity,Video_categories_Activity::class.java)
                    i.putExtra("title", data[position].title)
                    activity.startActivity(i)

                } else if (data[position].click == "videos list") {

                    val i = Intent(activity,Videos_list_Activity::class.java)
                    i.putExtra("title", data[position].title)
                    activity.startActivity(i)

                }

            }

//        fun getScreenOrientation():String{
//            var orientation = "undefined"
//
//            if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
//                orientation = "landscape"
//            }else if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
//                orientation = "portrait"
//            }
//
//            return orientation
//        }


    }

    override fun getItemCount(): Int {
        return data.size
    }

}
