package com.inspiredcoda.chatincognito.utils

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Fragment.dateDialog(date: (String) -> Unit) {
    val listener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        if (dayOfMonth != 0 && month != 0 && year != 0) {
            date("$dayOfMonth/$month/$year")
        }
    }
    val cal = Calendar.getInstance()

    DatePickerDialog(
        requireContext(),
        listener,
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    )
        .show()
}

fun Long.toDate(): String {
    val date = Date(this)
    return SimpleDateFormat("HH:SS", Locale.getDefault()).run {
        format(date)
    }
}