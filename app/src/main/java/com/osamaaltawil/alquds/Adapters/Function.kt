package com.osamaaltawil.alquds.Adapters

import android.app.Activity
import android.content.res.Configuration
import android.text.Layout
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.osamaaltawil.alquds.R
import kotlinx.android.synthetic.main.rc_items.*

class Function(var activity: Activity) {

    //This function to get Device Orientation
    fun getScreenOrientation():String{
        var orientation = "undefined";

        if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            orientation = "landscape";
        }else if(activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            orientation = "portrait";
        }

        return orientation;
    }
     // This function to get half screen width and resize width append to Device Orientation
    fun calculateWidth():Int{
       //  val layout=activity.findViewById<LinearLayout>(R.id.itemM_layout)
         var width:Int = 0
        val orientation=getScreenOrientation()
        val displayMetrics= DisplayMetrics()
        activity.windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        if (orientation == "landscape"){
            width=(displayMetrics.widthPixels/2)-80
            Log.e("Orientation",orientation)

        }else  if (orientation == "portrait"){
            width=(displayMetrics.widthPixels/2)-40
            Log.e("Orientation",orientation)


        }
        return width
    }
}