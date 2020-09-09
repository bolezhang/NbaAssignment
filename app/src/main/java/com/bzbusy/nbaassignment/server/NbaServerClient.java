package com.bzbusy.nbaassignment.server;

import android.content.Context;
import android.util.Log;

import com.bzbusy.nbaassignment.Util.Constant;
import com.bzbusy.nbaassignment.Util.Util;
import com.bzbusy.nbaassignment.listener.IServerCallback;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NbaServerClient {
    private static final String TAG = "Bole_NbaServerClient";
    private static final int DEFAULT_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;
    private OkHttpClient okHttpClient = null;
    private Context mContext;
    private static volatile NbaServerClient soleInstance = null;

    public static NbaServerClient getInstance(Context context) {
        if (soleInstance == null) {
            synchronized (NbaServerClient.class) {
                if (soleInstance == null) {
                    soleInstance = new NbaServerClient();
                    soleInstance.initCache(context);
                }
            }
        }
        return soleInstance;
    }

    public void initCache(Context context) {
        mContext = context;
        okHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(context.getCacheDir(), Constant.CACHE_FILE_SIZE))
                .addNetworkInterceptor(netCacheInterceptor)
                .addInterceptor(offlineCacheInterceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Cache when network available
     */
    final Interceptor netCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + Constant.NET_CACHE_MAX_AGE)
                    .removeHeader("Pragma")
                    .build();
        }
    };

    /**
     * Cache when offline
     */
    final Interceptor offlineCacheInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!Util.checkInternetConnection(mContext)) {
                request = request.newBuilder()
                        .cacheControl(new CacheControl
                                .Builder()
                                .maxStale(Constant.OFFLINE_CACHE_MAX_STALE, TimeUnit.HOURS)
                                .onlyIfCached()
                                .build()
                        )
                        .build();
            }
            return chain.proceed(request);
        }
    };

    private void getData(String url, @NonNull IServerCallback iServerCallback) {
        iServerCallback.onServerCommunicationStarted();

        final Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Log.d(TAG, "------Send request to server, url : " + url + "------");
        try (Response response = okHttpClient.newCall(request).execute()) {
            if (response.isSuccessful()) {
                if (response.networkResponse() != null) {
                    Log.d(TAG, "Got network response: " + response.networkResponse());
                    // Got network response
                    iServerCallback.onServerResultDelivered(response.code(), response.body().string());
                } else if (response.cacheResponse() != null) {
                    Log.d(TAG, "Got cache response: " + response.cacheResponse());
                    // Got Cache response
                    iServerCallback.onServerResultDelivered(response.code(), response.body().string());
                } else {
                    // Should never come here
                    iServerCallback.onServerCommunicationError("Server response failed, error code = " + response.code());
                }
            } else {
                iServerCallback.onServerCommunicationError("Server response failed, error code = " + response.code());
            }
        } catch (IOException e) {
            Log.e(TAG, "Communication error: " + e);
            iServerCallback.onServerCommunicationError(e.getMessage());
        }

        iServerCallback.onServerCommunicationFinished();
    }

    public void fetchData(final String url, final IServerCallback iServerCallback) {
        if (iServerCallback == null) {
            Log.e(TAG, "IServerCallback cannot be null, return");
            return;
        }

        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                soleInstance.getData(url, iServerCallback);
            }
        };
        BackgroundTasks.getInstance().runBackgroundMethod(runnable);
    }
}
