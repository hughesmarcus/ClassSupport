package com.supporter.marcus.classsupport.util.rx

import kotlinx.coroutines.experimental.android.UI

/**
 * Application providers
 */
class ApplicationSchedulerProvider : SchedulerProvider {
    override fun ui() = UI
}