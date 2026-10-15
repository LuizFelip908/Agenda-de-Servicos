package com.example.agendasdeservios.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    fun formatCurrency(value: Double): String {
        val locale = Locale.forLanguageTag("pt-BR")
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(value)
    }

    fun parseCurrency(valueString: String): Double? {
        val cleaned = valueString.replace("[^0-9,]".toRegex(), "").replace(",", ".")
        return cleaned.toDoubleOrNull()
    }
}
