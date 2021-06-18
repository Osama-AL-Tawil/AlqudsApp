package com.osamaaltawil.alquds

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.osamaaltawil.alquds.Adapters.NewsAdapter
import com.osamaaltawil.alquds.helper.Constance.API_KEY
import com.osamaaltawil.alquds.injector.Injector
import com.osamaaltawil.alquds.models.ApiModel
import com.osamaaltawil.alquds.service.ApiService
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class HomeFragment : Fragment() {
    lateinit var root: View
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var apiResponse: MutableList<ApiModel.News>
    private lateinit var apiService: ApiService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_home, container, false)

        apiResponse = ArrayList()

        apiService = Injector.provideDataService()!!


        if (MyApp.instance!!.isNetworkConnected()) {
            get_news_data()
        } else {
            get_news_data()
            toast_message("لا يوجد انترنت")
        }

        newsAdapter = NewsAdapter(activity!!, apiResponse)
        root.rvHome.adapter = newsAdapter

        return root
    }

    @SuppressLint("SimpleDateFormat")
    private fun get_news_data() {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        //set dialog
        val dialog=Custom_AlertDialog(activity!!)
        dialog.set_text_dialog(resources.getString(R.string.tv_Alert_loading))
        dialog.show_dialog()

        val yesterday = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate.now().minus(2, ChronoUnit.DAYS)
        } else {
//            var date = Date()
//            val formatter = SimpleDateFormat("MMM dd yyyy HH:mma")
//            formatter.format(date)

            TODO("VERSION.SDK_INT < O")
        }

        val formattedYesterday = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))


        apiService.getNews("القدس", formattedYesterday, currentDate, "popularity", API_KEY)
            .enqueue(object : Callback<ApiModel.Result> {

                override fun onFailure(call: Call<ApiModel.Result>, t: Throwable) {
                    Timber.e(t, "getString(R.string.error_occurred)")
                    dialog.dismiss_dialog()
                }


                override fun onResponse(call: Call<ApiModel.Result>, response: Response<ApiModel.Result>) {
                    if (response.isSuccessful && response.body()?.articles != null && response.body()!!.articles!!.isNotEmpty()) {
                        if (response.raw().networkResponse != null) {
                            Timber.i("onResponse: response is from NETWORK...")
                            //toast_message("onResponse: response is from NETWORK...")
                            toast_message(resources.getString(R.string.s_loading))

                        } else if (response.raw().cacheResponse != null && response.raw().networkResponse == null) {
                            Timber.i("onResponse: response is from CACHE...")
                            //toast_message("onResponse: response is from CACHE...")
                            toast_message(resources.getString(R.string.c_noInternet))

                        }

                        val data = response.body()
                        Log.e("responseL",data.toString())
                        apiResponse = data!!.articles!!.toMutableList()
                        newsAdapter.addData(apiResponse)
                        dialog.dismiss_dialog()

                    }
                }
            })
    }
    fun toast_message(message: String) {
        Toast.makeText(activity!!, message, Toast.LENGTH_LONG).show()
    }

}

