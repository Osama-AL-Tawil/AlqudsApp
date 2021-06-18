package com.osamaaltawil.alquds.Adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.osamaaltawil.alquds.Post_Details_Activity
import com.osamaaltawil.alquds.R
import com.osamaaltawil.alquds.models.ApiModel
import com.osamaaltawil.alquds.models.L_I_model
import kotlinx.android.synthetic.main.rc_home.view.*
import kotlinx.android.synthetic.main.rc_landmarks_item.view.*

class NewsAdapter(private val context: FragmentActivity, private var list: MutableList<ApiModel.News>) :
        RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    fun addData(list: MutableList<ApiModel.News>) {
        this.list = list
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.rc_home, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], context)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDescription: TextView? = itemView.tv_post_details
       // private val tvTitle: TextView? = itemView.tv_landmark_title
        private val image: ImageView? = itemView.img_post
        private val view: View = itemView

        fun bind(news: ApiModel.News, context: FragmentActivity) {
           // tvDescription!!.text = news.description
            tvDescription!!.text = news.title
            Glide.with(context)
                .load(news.urlToImage)
                .centerCrop()
                .into(image!!)

            view.setOnClickListener {

                val i = Intent(context, Post_Details_Activity::class.java)
                i.putExtra("fromHome", true)
                i.putExtra("data", ApiModel.News(
                    news.id,
                    news.author,
                    news.title,
                    news.description,
                    news.url,
                    news.urlToImage,
                    news.publishedAt,
                    news.content)
                )
                if (news.id !=null){
                    context.startActivity(i)
                    context.finish()

                }else{
                    context.startActivity(i)
                }

            }

        }
    }
}