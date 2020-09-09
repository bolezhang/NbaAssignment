package com.bzbusy.nbaassignment.listener;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by Bole Zhang on 2020-03-13.
 */
public interface IServerCallback {
    void onServerCommunicationStarted();
    void onServerCommunicationFinished();
    void onServerCommunicationError(String response);
    void onServerResultDelivered(int code, String response);
}
