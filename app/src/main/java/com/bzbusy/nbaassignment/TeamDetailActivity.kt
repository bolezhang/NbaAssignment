package com.bzbusy.nbaassignment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bzbusy.nbaassignment.Util.Constant
import com.bzbusy.nbaassignment.adapter.NbaTeamPlayerAdapter
import com.bzbusy.nbaassignment.model.NbaTeam
import com.bzbusy.nbaassignment.model.Player
import com.bzbusy.nbaassignment.presenter.NbaTeamPlayerPresenter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_team_detail.*
import java.util.*

class TeamDetailActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "TeamDetailActivity"
    }
    private lateinit var mNbaTeamPlayerAdapter: NbaTeamPlayerAdapter
    private lateinit var mNbaTeamPlayerPresenter: NbaTeamPlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val nbaTeam: NbaTeam = Gson().fromJson(intent.getStringExtra(Constant.EVENT_EXTRA), NbaTeam::class.java)
        val players = Gson().fromJson<ArrayList<Player>>(nbaTeam.players, object : TypeToken<List<Player?>?>() {}.type)
        tvDetailTeamTitle.text = nbaTeam.full_name
        tvDetailTeamWinLoss.text = (getString(R.string.team_win_loss, nbaTeam.wins, nbaTeam.losses))
        recyclerViewTeamRoster.layoutManager = LinearLayoutManager(this)
        recyclerViewTeamRoster.setHasFixedSize(true)

        // Init player list adapter
        mNbaTeamPlayerAdapter = NbaTeamPlayerAdapter(ArrayList())
        recyclerViewTeamRoster.adapter = mNbaTeamPlayerAdapter

        // Init player list presenter
        mNbaTeamPlayerPresenter = NbaTeamPlayerPresenter(this, players)
        mNbaTeamPlayerPresenter.loadData()
    }

    fun updateList(success: Boolean, players: List<Player?>?) {
        if (success) {
            mNbaTeamPlayerAdapter.clearData()
            mNbaTeamPlayerAdapter.addAllData(players)
            mNbaTeamPlayerAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(this, "Fail to get player data", Toast.LENGTH_LONG).show()
        }
    }
}