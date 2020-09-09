package com.bzbusy.nbaassignment.model

import com.google.gson.JsonArray

/**
 * Created by Bole Zhang on 2020-09-08.
 */
data class NbaTeam(var id: Int, var full_name: String, var wins: Int, var losses: Int, var players: JsonArray)