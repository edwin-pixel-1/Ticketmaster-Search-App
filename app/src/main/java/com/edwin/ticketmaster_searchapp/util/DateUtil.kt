package com.edwin.ticketmaster_searchapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DateUtil {

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        private val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)

        @RequiresApi(Build.VERSION_CODES.O)
        private val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("MMM d, yyyy - hh:mm a")
                .withZone(ZoneOffset.systemDefault())

        fun convertDateTimeUTC2Local(dateTime: String): String =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                outputFormatter.format(inputFormatter.parse(dateTime))
            } else {
                ""
            }

    }

}