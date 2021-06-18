package com.osamaaltawil.alquds.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

object ApiModel {
    data class Result(
        @SerializedName("articles")
        @Expose
        val articles: List<News>?) : Serializable

    data class News(
        val id: Int?,
        @SerializedName("author")
        @Expose
        val author: String?,
        @SerializedName("title")
        @Expose
        val title: String?,
        @SerializedName("description")
        @Expose
        val description: String?,
        @SerializedName("url")
        @Expose
        val url: String?,
        @SerializedName("urlToImage")
        @Expose
        val urlToImage: String?,
        @SerializedName("publishedAt")
        @Expose
        val publishedAt: String?,
        @SerializedName("content")
        @Expose
        val content: String?
    ) : Serializable {


        companion object {

        val COL_ID = "id"
        val COL_AUTHOR = "author"
        val COL_TITLE = "title"
        val COL_DES = "description"
        val COL_URL = "url"
        val COL_URL_TO_IMAGE = "urlToImage"
        val COL_PUBLISH_AT = "publishedAt"
        val COL_CONTENT = "content"

        val TABLE_NAME = "News"
        val TABLE_CREATE = "create table $TABLE_NAME ($COL_ID integer primary key autoincrement," +
                "$COL_AUTHOR text , $COL_TITLE text , $COL_DES text, $COL_URL text, $COL_URL_TO_IMAGE text, $COL_PUBLISH_AT text, $COL_CONTENT text)"
    }

    }

}