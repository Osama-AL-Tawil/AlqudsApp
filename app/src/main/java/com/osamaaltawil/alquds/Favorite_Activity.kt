package com.osamaaltawil.alquds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.osamaaltawil.alquds.Adapters.NewsAdapter
import com.osamaaltawil.alquds.db.DatabaseHelper
import kotlinx.android.synthetic.main.activity_favorite.*
import okhttp3.internal.notifyAll

class Favorite_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title="المفضلة"


        val db = DatabaseHelper(this)
        val data = db.getAllNews()

        rvFavorite.layoutManager = LinearLayoutManager(this)

        rvFavorite.setHasFixedSize(true)

        val newsAdapter = NewsAdapter(this, data)
        rvFavorite.adapter = newsAdapter



    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->this.finish()
        }
        return super.onOptionsItemSelected(item)
    }
}