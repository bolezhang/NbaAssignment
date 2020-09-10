package com.bzbusy.nbaassignment.presenter

import android.os.Handler
import android.os.Looper
import com.bzbusy.nbaassignment.DetailActivity
import com.bzbusy.nbaassignment.model.Player
import java.util.*

class NbaTeamPlayerPresenter(private val mView: DetailActivity, private val mPlayers: ArrayList<Player>) {
    fun loadData() {
        Handler(Looper.getMainLooper()).post {
            if (mPlayers != null || mPlayers.size > 0) {
                mView.updateList(true, mPlayers)
            } else {
                mView.updateList(false, null)
            }
        }
    }
}