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

    fun getTitleErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.quiz_title_cannot_be_empty
            input.length < 2 ->R.string.quiz_title_too_short
            input.any{it.isDigit()} -> R.string.quiz_title_cannot_contain_digits
            else -> null
        }
    }

    fun getCategoryErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.quiz_category_cannot_be_empty
            else -> null
        }
    }

    fun getPointsErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.quiz_points_cannot_be_empty
            input.toInt() < 10 ->R.string.quiz_points_too_small
            input.toInt() > 300 ->R.string.quiz_points_too_big
            else -> null
        }
    }

    fun getDifficultyLevelIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.difficulty_level_cannot_be_empty
            else -> null
        }
    }

    fun getQuestionErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.question_cannot_be_empty
            else -> null
        }
    }

    fun getAnswerErrorIdOrNull(input: String): Int? {
        return when {
            input.isEmpty() -> R.string.answer_cannot_be_empty
            else -> null
        }
    }

    fun getImageIdOrNull(input: String): Int? {
        return when {
            !Patterns.WEB_URL.matcher(input).matches() -> R.string.incorrect_image_url
            else -> null
        }
    }
}