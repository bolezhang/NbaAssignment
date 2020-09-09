package com.bzbusy.nbaassignment.server;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BackgroundTasks {
    private static final String TAG = "MYDEBUG_BackgroundTasks";
    private static volatile BackgroundTasks soleInstance = null;
    private ExecutorService singleExecutor;

    public static BackgroundTasks getInstance() {
        if (soleInstance == null) {
            synchronized (BackgroundTasks.class) {
                if (soleInstance == null) {
                    soleInstance = new BackgroundTasks();
                    soleInstance.singleExecutor = Executors.newSingleThreadExecutor();
                }
            }
        }
        return soleInstance;
    }

    public void runBackgroundMethod(Runnable runnable) {
        if (soleInstance != null && soleInstance.singleExecutor != null && !soleInstance.singleExecutor.isShutdown()) {
            soleInstance.singleExecutor.execute(runnable);
        } else {
            Log.e(TAG, "Single Thread Executor NOT READY");
        }
    }
}
