package com.supporter.marcus.classsupport

import android.app.Activity
import android.app.Application
import com.supporter.marcus.classsupport.di.onlineWeatherApp
import org.koin.android.ext.android.startKoin


class ChooseDonorApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // start Koin context
        startKoin(this,onlineWeatherApp)


    }
}