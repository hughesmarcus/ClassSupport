package com.supporter.marcus.classsupport.util.ext

import android.support.v4.app.FragmentActivity

/**
 * Retrieve argument from Activity intent
 */
fun <T : Any> FragmentActivity.argument(key: String) =
        lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }