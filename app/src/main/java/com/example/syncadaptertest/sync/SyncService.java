package com.example.syncadaptertest.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by tomer on 19/03/2016.
 *
 */
public class SyncService extends Service {

    private static final Object sSyncAdapterLock = new Object();
    private static SyncAdapter sSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("Sync", "Sync Service Start");
        Toast.makeText(getApplicationContext(), "onPreform Sync Start", Toast.LENGTH_LONG).show();
        synchronized (sSyncAdapterLock){
            if (sSyncAdapter == null){
                sSyncAdapter = new SyncAdapter(getApplicationContext(),true);
            }
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
