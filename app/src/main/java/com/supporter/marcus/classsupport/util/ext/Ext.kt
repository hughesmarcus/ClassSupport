package com.supporter.marcus.classsupport.util.ext

import android.support.v4.app.FragmentActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.supporter.marcus.classsupport.R

/**
 * Retrieve argument from Activity intent
 */
fun <T : Any> FragmentActivity.argument(key: String) =
        lazy { intent.extras[key] as? T ?: error("Intent Argument $key is missing") }

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

/**
 * For adapters
 */
fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Picasso.get().load(url).placeholder(R.drawable.progress_animation).fit().centerCrop().into(this)
}