package com.osamaaltawil.alquds.models

import java.io.Serializable

data class L_I_model(
    val name:String="",
    val img_array:ArrayList<String> = ArrayList(),
    val description:String="",
    val video_url:String="" ):Serializable
