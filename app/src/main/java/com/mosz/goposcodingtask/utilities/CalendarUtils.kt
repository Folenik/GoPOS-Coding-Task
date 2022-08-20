package com.mosz.goposcodingtask.utilities

import com.mosz.goposcodingtask.utilities.Constants.DATE_API_FORMAT
import com.mosz.goposcodingtask.utilities.Constants.DATE_UI_FORMAT
import java.text.SimpleDateFormat
import java.util.*

object CalendarUtils {
    fun today(): String {
        val dateFormat = SimpleDateFormat(DATE_API_FORMAT, Locale.getDefault())
        return dateFormat.format(Calendar.getInstance().time)
    }

    fun daysBefore(days: Int, from: String): String {
        val dateFormat = SimpleDateFormat(DATE_API_FORMAT, Locale.getDefault())
        val fromDate = dateFormat.parse(from) ?: Date()
        val date = Calendar.getInstance().apply {
            time = fromDate
            add(Calendar.DAY_OF_YEAR, -days)
        }
        return dateFormat.format(date.time)
    }

    fun mapDate(date: String): String {
        val dateFormatTo = SimpleDateFormat(DATE_UI_FORMAT, Locale.getDefault())
        val dateFormatFrom = SimpleDateFormat(DATE_API_FORMAT, Locale.getDefault())
        val parsedDate = dateFormatFrom.parse(date) ?: Date()
        return dateFormatTo.format(parsedDate)
    }
}