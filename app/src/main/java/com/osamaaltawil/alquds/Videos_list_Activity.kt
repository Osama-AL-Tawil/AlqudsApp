package com.osamaaltawil.alquds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.osamaaltawil.alquds.Adapters.Items_Adapter
import com.osamaaltawil.alquds.Adapters.Videos_Adapter
import com.osamaaltawil.alquds.models.L_I_model
import com.osamaaltawil.alquds.models.data_model
import com.osamaaltawil.alquds.models.video_model
import kotlinx.android.synthetic.main.activity_video_categories_.*
import kotlinx.android.synthetic.main.activity_videos_list_.*
import java.net.URL

class Videos_list_Activity : AppCompatActivity() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<video_model>()
    val TAG = "GET DATA ERROR"
    var title = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videos_list_)
        title = intent.getStringExtra("title")!!
        supportActionBar!!.title = title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //set recyclerView
        videos_list_rc.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        //Get data
        GetData()
    }

    fun GetData() {
        //show dialog
        val dialog = Custom_AlertDialog(this)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("informations").document("Videos").collection("data")
            .document(title).collection("videos").get()
            .addOnSuccessListener {
                if (!it.isEmpty) {
                    data_Array.addAll(it.toObjects(video_model::class.java))
                    setData(data_Array)
                    //dismiss alert dialog
                    dialog.dismiss_dialog()

                }
            }.addOnFailureListener {
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
                Log.e(TAG, it.message.toString())

            }
    }


    fun setData(data: ArrayList<video_model>) {
        val adapter = Videos_Adapter(this, data)
        videos_list_rc.adapter = adapter
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}