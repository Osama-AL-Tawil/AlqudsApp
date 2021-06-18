package com.osamaaltawil.alquds

import android.content.res.Configuration
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
import kotlinx.android.synthetic.main.fragment_landmark.view.*

class LandmarkFragment : Fragment() {

    lateinit var root: View
    var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    val data_Array = ArrayList<data_model>()
    val TAG="GET DATA ERROR"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_landmark, container, false)
       //set recyclerView
       root. recycler_landmarks.layoutManager=GridLayoutManager(activity,2)


      //Get data
        GetData()

        return root
    }

    fun GetData() {

        //show dialog
        val dialog=Custom_AlertDialog(activity!!)
        dialog.show_dialog()
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))

        db.collection("Landmarks").get()
            .addOnSuccessListener {
                if (!it.isEmpty){
                    data_Array.addAll(it.toObjects(data_model::class.java))
                    setData(data_Array)
                    dialog.dismiss_dialog()
                }
            }.addOnFailureListener {
                Toast.makeText(activity!!,it.message.toString(),Toast.LENGTH_LONG).show()
                Log.e(TAG,it.message.toString())

            }
    }

    fun setData(data:ArrayList<data_model>){
      val adapter=Items_Adapter(activity!!,data)
        root.recycler_landmarks.adapter=adapter

    }
fun calculateNumberOfColumns(base:Int):Int{
    var columns = base;
    var screenSize = getScreenSizeCategory();

    if(screenSize.equals("small")){
        if(base!=1){
            columns = columns-1;
        }
    }else if (screenSize.equals("normal")){
        // Do nothing
    }else if(screenSize.equals("large")){
        columns += 2;
    }else if (screenSize.equals("xlarge")){
        columns += 3;
    }

    if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
        columns = columns * 1.5.toInt()
    }

    return columns;
}

    fun getScreenOrientation():String{
        var orientation = "undefined";

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientation = "landscape";
        }else if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation = "portrait";
        }

        return orientation;
    }


    // Custom method to get screen size category
    fun getScreenSizeCategory():String{
        var size=""
        val screenLayout = getResources().getConfiguration().screenLayout+Configuration.SCREENLAYOUT_SIZE_MASK;

        when(screenLayout){
             Configuration.SCREENLAYOUT_SIZE_SMALL->
            // small screens are at least 426dp x 320dp
            size=="small";
             Configuration.SCREENLAYOUT_SIZE_NORMAL->
            // normal screens are at least 470dp x 320dp
            size=="normal";
             Configuration.SCREENLAYOUT_SIZE_LARGE->
            // large screens are at least 640dp x 480dp
            size=="large";
             Configuration.SCREENLAYOUT_SIZE_XLARGE->
            // xlarge screens are at least 960dp x 720dp
            size== "xlarge";

        }
        Log.e("size",size)
        return size
    }

}

