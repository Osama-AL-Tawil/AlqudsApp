package com.osamaaltawil.alquds

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.osamaaltawil.alquds.db.DatabaseHelper
import com.osamaaltawil.alquds.models.ApiModel
import com.osamaaltawil.alquds.models.L_I_model
import kotlinx.android.synthetic.main.activity_post__details_.*
import java.io.Serializable
import java.lang.RuntimeException

class Post_Details_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post__details_)

        val db = DatabaseHelper(this)
        //var img_count=""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title=resources.getString(R.string.tv_details)

        //get and set data.....
        //when set and show data from (Api News)----------------------------------------------------------------
        if(intent.getBooleanExtra("fromHome", false)){

            val data = intent.getSerializableExtra("data") as ApiModel.News

            //set image array in view pager
            val img_array:ArrayList<String> = ArrayList()
            img_array.add(data.urlToImage!!)

            val url=data.url.toString()

             //set data in Views------
             set_data(data.title,data.description,img_array,data.publishedAt,url)


            tv_url_news.setOnClickListener {
                if (url != "") {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(url)
                    try {
                        ContextCompat.startActivity(this, openURL, null)

                    } catch (e: RuntimeException) {
                        Toast.makeText(this, "خطأ في رابط الخبر", Toast.LENGTH_LONG).show()

                    }
                }
            }




            //-------------------------------------------------------------------------------------------------------
            //show delete button when post from Local DB
            if (data.id != null) {
                cardView.visibility = View.VISIBLE
                btn_delete.visibility = View.VISIBLE
                btn_save.visibility = View.GONE


            } else {
                //show save button when post from api
                cardView.visibility = View.VISIBLE
                btn_save.visibility = View.VISIBLE
            }

            //btn save post in favorite
            btn_save.setOnClickListener {
                if (db.insertNews(data))
                    Toast.makeText(this, resources.getString(R.string.add_to_favorite), Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, resources.getString(R.string.failed_add_to_favorite), Toast.LENGTH_SHORT).show()
            }

            //btn delete post from DB
            btn_delete.setOnClickListener {
                db.deleteNews(data.id!!.toInt())
                startActivity(Intent(this, Favorite_Activity::class.java))
                finish()
            }


        }else{
            //when set and show data from Firestore------------------------------------------------------------
            val data = intent.getSerializableExtra("data") as L_I_model

            //set data in Views------
            set_data(
                data.name,
                data.description,
                data.img_array,
                "", ""
            )

        }



        // create view pager and set images and adapter
//        val  adapter: PagerAdapter = SliderAdapter(this, data.img_array)

//        val image_count=data.img_array.size

//        img_count.setText("1"+"/"+image_count.toString())

//        img_ViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
//
//
//            override fun onPageSelected(position: Int) {
//                val pos=position+1
//                //img_count.setText(pos.toString()+"/"+image_count.toString())
//            }
//        })

//        img_ViewPager.offscreenPageLimit
//
//        img_ViewPager.adapter=adapter





    }

fun set_data(title:String?,description:String?,img_Array:ArrayList<String>,date:String?,news_url:String?){

    //set title TextView
    tv_title.text = title

    //set description TextView
    tv_description.text = description

    //set date TextView
    tv_date.text = date

    // set url and view news source
    if (news_url!=""){
        tv_url_news.setText("رابط الخبر : \n $news_url")
    }

    //set image array in adapter
    val adapter: PagerAdapter = SliderAdapter(this, img_Array)
    img_ViewPager.offscreenPageLimit
    img_ViewPager.adapter = adapter




}

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}