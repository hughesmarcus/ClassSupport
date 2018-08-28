package com.supporter.marcus.classsupport

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.supporter.marcus.classsupport.di.onlineDonorApp
import io.fabric.sdk.android.Fabric
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.android.startKoin


class ChooseDonorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupCrashlytics()
        // start Koin context
        startKoin(this, onlineDonorApp)
        JodaTimeAndroid.init(this)

    }

    private fun setupCrashlytics() {
        val core = CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build()
        Fabric.with(this, Crashlytics.Builder().core(core).build())
    }
}