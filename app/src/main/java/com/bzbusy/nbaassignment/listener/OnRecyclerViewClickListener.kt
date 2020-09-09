package com.bzbusy.nbaassignment.listener

import android.view.View

interface OnRecyclerViewClickListener {
    fun onRecyclerViewClick(clickedObject: Any?, transitionView1: View?, transitionView2: View?)
}