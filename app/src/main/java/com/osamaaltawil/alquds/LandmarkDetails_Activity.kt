package com.osamaaltawil.alquds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.osamaaltawil.alquds.Adapters.Landmarks_Adapter
import com.osamaaltawil.alquds.models.L_I_model
import kotlinx.android.synthetic.main.activity_landmark_details_.*

class LandmarkDetails_Activity : AppCompatActivity() {

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<L_I_model>()
    val TAG="GET DATA ERROR"
    lateinit var title:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landmark_details_)
        //set recycler layout.............
recycler_landmarks_details.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        title=intent.getStringExtra("title").toString()
        supportActionBar!!.title=title
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        GetData()

    }


    fun GetData() {
        //show dialog
        val dialog=Custom_AlertDialog(this)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("Landmarks").document(title).collection("data").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    data_Array.addAll(it.toObjects(L_I_model::class.java))
                    setData(data_Array)
                    dialog.dismiss_dialog()

                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(), Toast.LENGTH_LONG).show()
                Log.e(TAG,it.message.toString())

            }
    }


    fun setData(data:ArrayList<L_I_model>){
        val adapter= Landmarks_Adapter(this,data)
        recycler_landmarks_details.adapter=adapter
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}