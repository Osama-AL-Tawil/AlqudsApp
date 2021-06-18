package com.osamaaltawil.alquds.interceptor

import com.osamaaltawil.alquds.MyApp
import com.osamaaltawil.alquds.helper.Constance.HEADER_CACHE_CONTROL
import com.osamaaltawil.alquds.helper.Constance.HEADER_PRAGMA
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.TimeUnit

open class OfflineCacheInterceptorWithHeader : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val header = request.headers["Cacheable"]

        if (header != null) {
            if (header == "true" && !MyApp.instance!!.isNetworkConnected()) {
                Timber.d("CACHE Header: called.::%s", header)
                Timber.d("cache interceptor: called.")
                val cacheControl = CacheControl.Builder()
                    .maxStale(7, TimeUnit.DAYS)
                    .build()

                request = request.newBuilder()
                    .removeHeader(HEADER_PRAGMA)
                    .removeHeader(HEADER_CACHE_CONTROL)
                    .cacheControl(cacheControl)
                    .build()
            } else {
                Timber.d("cache interceptor: not called.")
            }
        }
        return chain.proceed(request)
    }
}