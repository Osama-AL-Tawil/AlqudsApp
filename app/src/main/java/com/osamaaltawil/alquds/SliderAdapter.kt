package com.osamaaltawil.alquds

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class SliderAdapter(var context: Context, var images: ArrayList<String>) : PagerAdapter() {

    lateinit var inflater:LayoutInflater

    override fun isViewFromObject(view: View, `object`: Any): Boolean =view== `object` as FrameLayout


    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val image:ImageView
        var text1:TextView
        //inflate layout
        inflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)as LayoutInflater

        val view:View=inflater.inflate(R.layout.slider_image_item,container,false)
       // val view2:View=inflater.inflate(R.layout.rc_post_image,container,false)
        //get imageView
        image=view.findViewById(R.id.slider_image)

        //load array list for images in image view using Glide library
        Glide.with(context).load(images[position]).into(image)

        //show full screen image on click image
//        image.setOnClickListener {
//            StfalconImageViewer.Builder(context,images) { view, image ->
//                Glide.with(context).load(images[position]).into(view)
//
//            }.show()
//        }
//text1=view2.findViewById(R.id.text_cont_1)
//        val pos=position+1
//       text1.setText(pos.toString())
//Log.e("arr","array position $pos")
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as FrameLayout)
    }
}