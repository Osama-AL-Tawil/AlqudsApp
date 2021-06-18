package com.osamaaltawil.alquds

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.osamaaltawil.alquds.Adapters.Items_Adapter
import com.osamaaltawil.alquds.models.data_model
import kotlinx.android.synthetic.main.fragment_informations.view.*


class InformationsFragment : Fragment() {
  lateinit var root:View
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<data_model>()
    val TAG="GET DATA ERROR"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root= inflater.inflate(R.layout.fragment_informations, container, false)
        //set recyclerView
        root.recycler_informations.layoutManager= GridLayoutManager(activity,2)
        //Get data
        GetData()

        return root
    }
    fun GetData() {
        //show dialog
        val dialog=Custom_AlertDialog(activity!!)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("informations").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    data_Array.addAll(it.toObjects(data_model::class.java))
                    setData(data_Array)
                    //dismiss alert dialog
                    dialog.dismiss_dialog()

                }
            }.addOnFailureListener {
                Toast.makeText(activity,it.message.toString(), Toast.LENGTH_LONG).show()
                Log.e(TAG,it.message.toString())

            }
    }


    fun setData(data:ArrayList<data_model>){
        val adapter= Items_Adapter(activity!!,data)
       root.recycler_informations.adapter=adapter
    }
}