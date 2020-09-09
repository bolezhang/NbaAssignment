package com.bzbusy.nbaassignment.model

/**
 * Created by Bole Zhang on 2020-09-08.
 */
data class Player(var id: Int, var first_name: String, var last_name: String, var position: String, var number: Int)
// Keep the field name the same as json field for faster Gson convert use