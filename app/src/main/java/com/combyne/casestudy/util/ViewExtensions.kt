package com.combyne.casestudy.util

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.bumptech.glide.Glide

fun View.visible() {
    if (visibility == View.GONE || visibility == View.INVISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

fun EditText.isEmptyNullOrBlank():Boolean{
    return text.isNullOrBlank() || text.isNullOrEmpty()
}