package com.osamaaltawil.alquds.models

import java.io.Serializable

data class video_model(
    val title:String="",
    var description:String="",
    val video_url:String="",
    val video_source:String="",
    val img_url:String=""):Serializable
