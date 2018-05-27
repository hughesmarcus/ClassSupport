package com.supporter.marcus.classsupport.util

import com.supporter.marcus.classsupport.util.rx.SchedulerProvider
import kotlinx.coroutines.experimental.Unconfined

class TestSchedulerProvider : SchedulerProvider {
    override fun ui() = Unconfined
}