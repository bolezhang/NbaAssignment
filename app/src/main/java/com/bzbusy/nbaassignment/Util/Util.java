package com.bzbusy.nbaassignment.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.bzbusy.nbaassignment.model.NbaTeam;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by Bole Zhang on 2020-09-07.
 */
public class Util {
    private static final String TAG = "Bole_Util";

    public static boolean checkInternetConnection(@NonNull Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            Log.e(TAG, "UtilNetwork checkInternetConnection ConnectivityManager is null");
            return false;
        }
        final NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static List<NbaTeam> sortTeamsByKeys(List<NbaTeam> list, final Constant.Sort sort) {
        Collections.sort(list, new Comparator<NbaTeam>() {
            @Override
            public int compare(NbaTeam lhs, NbaTeam rhs) {
                switch (sort) {
                    case Wins:
                        return Integer.compare(rhs.getWins(), lhs.getWins());
                    case Losses:
                        return Integer.compare(lhs.getLosses(), rhs.getLosses());
                    default:
                        // Default sort by team name
                        final String lName = lhs.getFull_name();
                        final String rName = rhs.getFull_name();
                        return lName.compareToIgnoreCase(rName);
                }
            }
        });
        return list;
    }
}
