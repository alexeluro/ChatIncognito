package com.inspiredcoda.chatincognito.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.show(shouldShow: Boolean) {
    visibility = if (shouldShow) View.VISIBLE else View.GONE
}

fun Fragment.snackbar(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackbar(message: String, retryAction: () -> Unit) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        .setAction("retry") {
            retryAction()
        }.show()

}

fun Fragment.hideKeyboard() {
    val inputMethodMethod =
        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodMethod.hideSoftInputFromWindow(requireView().windowToken, 0)
}

