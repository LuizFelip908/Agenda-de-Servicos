package com.example.agendasdeservios.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateUtils {

    private val ptBrLocale = Locale.forLanguageTag("pt-BR")

    fun formatDateShort(millis: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", ptBrLocale)
        return sdf.format(Date(millis))
    }

    fun formatDateFull(millis: Long): String {
        val sdf = SimpleDateFormat("EEE, dd 'de' MMMM 'de' yyyy", ptBrLocale)
        return sdf.format(Date(millis)).replaceFirstChar { it.uppercase() }
    }

    fun formatTime(millis: Long): String {
        val sdf = SimpleDateFormat("HH:mm", ptBrLocale)
        return sdf.format(Date(millis))
    }

    fun formatRange(startMillis: Long, endMillis: Long): String {
        return "${formatTime(startMillis)} - ${formatTime(endMillis)}"
    }

    fun getStartOfDay(millis: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    fun getEndOfDay(millis: Long = System.currentTimeMillis()): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = millis
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.timeInMillis
    }

    fun isToday(millis: Long): Boolean {
        val todayStart = getStartOfDay()
        val todayEnd = getEndOfDay()
        return millis in todayStart..todayEnd
    }
}
