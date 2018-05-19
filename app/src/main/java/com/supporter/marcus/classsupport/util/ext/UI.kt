package com.supporter.marcus.classsupport.util.ext

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.support.annotation.StringRes
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun Activity.hideKeyboard() {
    if (currentFocus != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
                currentFocus.windowToken,
                InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }
}

fun Activity.showKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun Activity.showToast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) =
        showToast(getString(resource), duration)

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
        Toast.makeText(this, message, duration).show()

fun Fragment.showToast(@StringRes resource: Int, duration: Int = Toast.LENGTH_SHORT) =
        showToast(getString(resource), duration)

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) =
        activity?.showToast(message, duration)
