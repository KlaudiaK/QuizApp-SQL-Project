package com.android.quizzy.utils

import android.content.Context
import android.widget.Toast

fun Context.showToastMessage(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()