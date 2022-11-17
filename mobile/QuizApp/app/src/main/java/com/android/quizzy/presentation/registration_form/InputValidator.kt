package com.android.quizzy.presentation.registration_form

import android.util.Patterns
import com.android.quizzy.R

object InputValidator {

    fun getUsernameErrorIdOrNull(input: String): Int? {
        return when {
            input.length < 2 -> R.string.username_too_short
            input.all { it.isDigit() }  -> R.string.username_pattern
            else -> null
        }
    }
    fun getFirstnameErrorIdOrNull(input: String): Int? {
        return when {
            input.length < 2 -> R.string.name_too_short
            input.any{it.isDigit()} -> R.string.name_cannot_contain_digits
            else -> null
        }
    }



    fun getEmailErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.email_cannot_be_empty
            !Patterns.EMAIL_ADDRESS.matcher(input).matches() -> R.string.email_doesnt_mach_pattern
            else -> null
        }
    }

    fun getPasswordErrorIdOrNull(input: String): Int? {
        return when {
            input.length < 8 -> R.string.password_too_short
            !(input.any { it.isDigit() } &&
                    input.any { it.isLetter() }) -> R.string.password_pattern
            else -> null
        }
    }

    fun getRepeatedPasswordErrorIdOrNull(input: String, patternInput: String): Int? {
        return when {
            input != patternInput -> R.string.repeated_password_doesnt_match
            else -> null
        }
    }

}