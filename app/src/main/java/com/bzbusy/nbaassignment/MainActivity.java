package com.bzbusy.nbaassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bzbusy.nbaassignment.Util.Constant;
import com.bzbusy.nbaassignment.adapter.NbaTeamListAdapter;
import com.bzbusy.nbaassignment.listener.OnRecyclerViewClickListener;
import com.bzbusy.nbaassignment.model.NbaTeam;
import com.bzbusy.nbaassignment.presenter.NbaTeamListPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements OnRecyclerViewClickListener {
    private static final String TAG = "MainActivity";
    private RecyclerView mRecyclerViewTeamList;
    private NbaTeamListAdapter mNbaTeamListAdapter;
    private NbaTeamListPresenter mNbaTeamListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Recycler view
        mRecyclerViewTeamList = findViewById(R.id.recyclerViewTeamList);
        mRecyclerViewTeamList.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewTeamList.setHasFixedSize(true);

        // Init team list adapter
        mNbaTeamListAdapter = new NbaTeamListAdapter(this, new ArrayList<NbaTeam>(), this);
        mRecyclerViewTeamList.setAdapter(mNbaTeamListAdapter);

        // Init team list presenter
        mNbaTeamListPresenter = new NbaTeamListPresenter(this, getApplicationContext());
        mNbaTeamListPresenter.loadData();
    }

    public void updateList(boolean success, List<NbaTeam> teams) {
        if (success) {
            mNbaTeamListAdapter.clearData();
            mNbaTeamListAdapter.addAllData(teams);
            refreshUI(mNbaTeamListAdapter.getCurrentSort());
        } else {
            Toast.makeText(this, "Fail to get data", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshUI(Constant.Sort sort) {
        mNbaTeamListAdapter.sortData(sort);
        mNbaTeamListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRecyclerViewClick(Object clickedObject, View transitionView1, View transitionView2) {
        Log.d(TAG, "onRecyclerViewClick called");

        final NbaTeam nbaTeam = (NbaTeam) clickedObject;
        final Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constant.EVENT_EXTRA, new Gson().toJson(nbaTeam));

        // Add scene transition animation
        final Pair<View, String> p1 = Pair.create(transitionView1, getString(R.string.sharedTeamName));
        final Pair<View, String> p2 = Pair.create(transitionView2, getString(R.string.sharedTeamWinLoss));
        final ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2);
        startActivity(intent, options.toBundle());
    }


    // -------------Menu--------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter_team_name:
                refreshUI(Constant.Sort.TeamName);
                Toast.makeText(MainActivity.this, getString(R.string.team_sort_by_name), Toast.LENGTH_LONG).show();
                return true;
            case R.id.filter_team_win:
                refreshUI(Constant.Sort.Wins);
                Toast.makeText(MainActivity.this, getString(R.string.team_sort_by_win), Toast.LENGTH_LONG).show();
                return true;
            case R.id.filter_team_loss:
                refreshUI(Constant.Sort.Losses);
                Toast.makeText(MainActivity.this, getString(R.string.team_sort_by_loss), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
