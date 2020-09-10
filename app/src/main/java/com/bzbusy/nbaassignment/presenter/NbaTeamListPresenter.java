package com.bzbusy.nbaassignment.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bzbusy.nbaassignment.MainActivity;
import com.bzbusy.nbaassignment.Util.Constant;
import com.bzbusy.nbaassignment.listener.IServerCallback;
import com.bzbusy.nbaassignment.model.NbaTeam;
import com.bzbusy.nbaassignment.server.NbaServerClient;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by datct0407 on 10/6/15.
 */
public class NbaTeamListPresenter {
    private static final String TAG = "NbaTeamListPresenter";
    private MainActivity mView;
    private Context mContext;

    public NbaTeamListPresenter(MainActivity view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public void loadData() {
        NbaServerClient.getInstance(mContext).fetchData(Constant.MOCK_DATA_URL, new IServerCallback() {
            @Override
            public void onServerCommunicationStarted() {
                Log.d(TAG, "onServerCommunicationStarted called");
            }

            @Override
            public void onServerCommunicationFinished() {
                Log.d(TAG, "onServerCommunicationFinished called");
            }

            @Override
            public void onServerCommunicationError(String response) {
                Log.d(TAG, "onServerCommunicationError: " + response);
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateList(false, null);
                    }
                });
            }

            @Override
            public void onServerResultDelivered(int responseCode, String response) {
                Log.d(TAG, "onServerResultDelivered, responseCode = " + responseCode + ", response = " + response);

                final JsonArray jsonArray = new JsonParser().parse(response).getAsJsonArray();
                final ArrayList<NbaTeam> teamList = new Gson().fromJson(jsonArray, new TypeToken<List<NbaTeam>>() {
                }.getType());
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        mView.updateList(true, teamList);
                    }
                });
            }
        });
    }
}