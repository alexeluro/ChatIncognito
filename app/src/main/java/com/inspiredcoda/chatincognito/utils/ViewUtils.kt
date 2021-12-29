package com.inspiredcoda.chatincognito.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

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

fun Fragment.hasCameraPermission(isGranted: (Boolean) -> Unit) {
    val hasPermission = ContextCompat.checkSelfPermission(
        requireActivity(),
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
    isGranted(hasPermission)
}

fun Fragment.createImageFile(): File {
    val timeStamp = SimpleDateFormat.getDateTimeInstance().format(Date())
    val storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(
        "profilepic_${timeStamp}_",
        ".jpg",
        storageDir
    )
}