package com.osamaaltawil.alquds

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.osamaaltawil.alquds.interceptor.NetworkInterceptor
import com.osamaaltawil.alquds.interceptor.OfflineCacheInterceptor
import com.osamaaltawil.alquds.interceptor.OfflineCacheInterceptorWithHeader
import com.osamaaltawil.alquds.service.ApiService
import com.osamaaltawil.alquds.helper.Constance.BASE_URL
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import timber.log.Timber.DebugTree

class MyApp : Application() {
    private lateinit var apiService: ApiService
    val networkInterceptor = NetworkInterceptor()
    val offlineCacheInterceptor = OfflineCacheInterceptor()

    init {
        instance = this
    }

    companion object {
        var instance: MyApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        //Gson Builder
//        val gsonBuilder = GsonBuilder()
//        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
//        val gson = gsonBuilder.create()
//        Timber.plant(DebugTree())


        // HttpLoggingInterceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message: String? -> Timber.i(message) }
        )

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

       // val offlineCacheInterceptorWithHeader = OfflineCacheInterceptorWithHeader()


        // OkHttpClient
        val okHttpClient: OkHttpClient = OkHttpClient().newBuilder()
            .cache(cache())
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(networkInterceptor) // only used when network is on
            .addInterceptor(offlineCacheInterceptor)
            .build()


        //Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
             apiService = retrofit.create(ApiService::class.java)
    }


    fun getApiService(): ApiService {
        return apiService
    }


    private fun cache(): Cache {
        val cacheSize = 5 * 1024 * 1024.toLong()
        return Cache(instance!!.applicationContext.cacheDir, cacheSize)
    }


    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}