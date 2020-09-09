package com.bzbusy.nbaassignment.Util

/**
 * Created by Bole Zhang on 2020-09-07.
 */
object Constant {
    const val NET_CACHE_MAX_AGE = 60 * 60 // 1 hour
    const val OFFLINE_CACHE_MAX_STALE = 24 // 24 hours
    const val CACHE_FILE_SIZE = 10 * 1024 * 1024.toLong()
    const val MOCK_DATA_URL = "https://raw.githubusercontent.com/scoremedia/nba-team-viewer/master/input.json"
    const val EVENT_EXTRA = "EVENT_EXTRA"

    enum class Sort {
        TeamName, Wins, Losses // TODO more sort detail, winAsc, winDes, lossesAsc, lossesDes
    }
}