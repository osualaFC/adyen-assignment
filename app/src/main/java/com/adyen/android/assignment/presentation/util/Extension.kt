package com.adyen.android.assignment.presentation.util

import android.view.View

object Extension {

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

}