package com.osamaaltawil.alquds.db

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.osamaaltawil.alquds.models.ApiModel

class DatabaseHelper(activity: Activity) :
    SQLiteOpenHelper(activity, DATABASE_NAME, null, DATABASE_VERSION) {

    private val db: SQLiteDatabase = this.writableDatabase
    var boolean=false

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(ApiModel.News.TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if exists ${ApiModel.News.TABLE_NAME}")
        onCreate(db)
    }

    //==============================================================================================
    //DML

    fun insertNews(news: ApiModel.News): Boolean {
        val cv = ContentValues()
        cv.put(ApiModel.News.COL_AUTHOR, news.author)
        cv.put(ApiModel.News.COL_TITLE, news.title)
        cv.put(ApiModel.News.COL_CONTENT, news.content)
        cv.put(ApiModel.News.COL_URL, news.url)
        cv.put(ApiModel.News.COL_PUBLISH_AT, news.publishedAt)
        cv.put(ApiModel.News.COL_URL_TO_IMAGE, news.urlToImage)
        cv.put(ApiModel.News.COL_DES, news.description)
        return db.insert(ApiModel.News.TABLE_NAME, null, cv) > 0
    }

    fun getAllNews(): ArrayList<ApiModel.News> {
        val data = ArrayList<ApiModel.News>()
        val c =
            db.rawQuery("select * from ${ApiModel.News.TABLE_NAME} order by ${ApiModel.News.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val s = ApiModel.News(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6),  c.getString(7))
            data.add(s)
            c.moveToNext()
        }
        c.close()
        return data
    }

    fun deleteNews(id:Int) : Boolean{
        //return db.delete(Student.TABLE_NAME,"${Student.COL_ID} = ?", arrayOf(id.toString()))>0
        return db.delete(ApiModel.News.TABLE_NAME,"${ApiModel.News.COL_ID} = $id",null)>0
    }
    //check if post in db
    fun check(id: Int): Boolean {
        val cursor = db.rawQuery(
            "SELECT ${ApiModel.News.COL_ID} FROM " + ApiModel.News.TABLE_NAME + " WHERE " + "${ApiModel.News.COL_ID} = $id",
            null
        )
        cursor.moveToFirst()

        if (cursor.getInt(0) == id) {
            boolean = true
        } else {
            cursor.close()

        }

        return boolean
    }




    companion object {
        val DATABASE_NAME = "University"
        val DATABASE_VERSION = 1

    }
}