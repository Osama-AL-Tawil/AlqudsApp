package com.osamaaltawil.alquds.injector

import com.osamaaltawil.alquds.MyApp
import com.osamaaltawil.alquds.service.ApiService

class Injector {
    companion object {
        fun provideDataService(): ApiService? {
            return MyApp.instance!!.getApiService()
        }
    }
}