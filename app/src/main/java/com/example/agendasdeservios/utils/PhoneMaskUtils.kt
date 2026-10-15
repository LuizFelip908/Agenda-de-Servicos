package com.example.agendasdeservios.utils

object PhoneMaskUtils {

    fun formatPhone(phone: String): String {
        val digitsOnly = phone.filter { it.isDigit() }
        return when {
            digitsOnly.length <= 2 -> digitsOnly
            digitsOnly.length <= 6 -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2)}"
            digitsOnly.length <= 10 -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2, 6)}-${digitsOnly.substring(6)}"
            digitsOnly.length <= 11 -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2, 7)}-${digitsOnly.substring(7)}"
            else -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2, 7)}-${digitsOnly.substring(7, 11)}"
        }
    }

    fun unformat(phone: String): String {
        return phone.filter { it.isDigit() }
    }

    fun isValidPhone(phone: String): Boolean {
        val digits = unformat(phone)
        return digits.length in 10..11
    }
}
