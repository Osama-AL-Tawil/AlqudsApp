package com.osamaaltawil.alquds

import android.app.Activity
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.custom_dialog.view.*

class Custom_AlertDialog (val activity: Activity){
    val view= LayoutInflater.from(activity).inflate(R.layout.custom_dialog,null)
    val  Builder= AlertDialog.Builder(activity,R.style.AlertDialog)

     var dialog: AlertDialog?=null

    fun show_dialog(){
        Builder.setView(view)
        Builder.setCancelable(false)
        dialog=Builder.create()
        dialog!!.show()
    }

    fun dismiss_dialog(){
        Log.e("dialog","dismiss")
        dialog!!.dismiss()

   }

    fun set_text_dialog(text:String){
        view.textView.setText(text)
    }

}