package com.osamaaltawil.alquds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.osamaaltawil.alquds.Adapters.GInformations_Adapter
import com.osamaaltawil.alquds.models.L_I_model
import kotlinx.android.synthetic.main.activity_g_informations_list_.*

class GInformations_list_Activity : AppCompatActivity() {
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<L_I_model>()
    val TAG="GET DATA ERROR"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_informations_list_)

        val title=intent.getStringExtra("title")
        supportActionBar!!.title=title

        //set recyclerView
        GInformation_rc.layoutManager= GridLayoutManager(this,2)
        //Get data
        GetData()
    }
    fun GetData() {
        //show dialog
        val dialog=Custom_AlertDialog(this)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("informations").document("General information")
            .collection("data").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    data_Array.addAll(it.toObjects(L_I_model::class.java))
                    setData(data_Array)
                    //dismiss alert dialog
                    dialog.dismiss_dialog()

                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(), Toast.LENGTH_LONG).show()
                Log.e(TAG,it.message.toString())

            }
    }


    fun setData(data:ArrayList<L_I_model>){
        val adapter= GInformations_Adapter(this,data)
        GInformation_rc.adapter=adapter
    }
}