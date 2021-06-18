package com.osamaaltawil.alquds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.osamaaltawil.alquds.Adapters.Items_Adapter
import com.osamaaltawil.alquds.models.data_model
import kotlinx.android.synthetic.main.activity_g_informations_list_.*
import kotlinx.android.synthetic.main.activity_video_categories_.*
import kotlinx.android.synthetic.main.fragment_informations.view.*

class Video_categories_Activity : AppCompatActivity() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<data_model>()
    val TAG="GET DATA ERROR"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_categories_)

        val title=intent.getStringExtra("title")
        supportActionBar!!.title=title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //set recyclerView
        videoCategories_rc.layoutManager= GridLayoutManager(this,2)
        //Get data
        GetData()
    }

    fun GetData() {
        //show dialog
        val dialog=Custom_AlertDialog(this)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("informations").document("Videos").collection("data").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    data_Array.addAll(it.toObjects(data_model::class.java))
                    setData(data_Array)
                    //dismiss alert dialog
                    dialog.dismiss_dialog()

                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(), Toast.LENGTH_LONG).show()
                Log.e(TAG,it.message.toString())

            }
    }


    fun setData(data:ArrayList<data_model>){
        val adapter= Items_Adapter(this,data)
        videoCategories_rc.adapter=adapter
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}