package com.supporter.marcus.classsupport

import android.app.Activity
import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.supporter.marcus.classsupport.di.onlineWeatherApp
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin


class ChooseDonorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupCrashlytics()
        // start Koin context
        startKoin(this,onlineWeatherApp)


    }

    private fun setupCrashlytics() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }
}