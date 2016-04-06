package com.example.syncadaptertest.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.syncadaptertest.MainActivity;
import com.example.syncadaptertest.R;

/**
 * Created by tomer on 16/03/2016.
 * scheduling packages status check
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.d("Sync", "onPreform Sync Start");
        Toast.makeText(getContext(),"onPreform Sync Start",Toast.LENGTH_LONG).show();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getContext())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getContext(), MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getContext());
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    public static void syncImmediately(Context context){
        Log.d("Sync", "Sync Immediately Fire");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        Account account = getSyncAccount(context);
        //ContentResolver.setIsSyncable(account,context.getString(R.string.content_authority),1);
        if(account == null){
            Log.d("SyncTest","Account is null");
        }else{
            Log.d("SyncTest","requestSync ws made");
            ContentResolver.requestSync(
                    account,
                    context.getString(R.string.content_authority),
                    bundle
            );
        }
    }

    public static Account getSyncAccount(Context context){

        AccountManager accountManager = (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);

        Account account = new Account(context.getString(R.string.app_name),context.getString(R.string.sync_account_type));

        if(accountManager.getPassword(account) == null){
            if(!accountManager.addAccountExplicitly(account,"",null)){
                return null;
            }
        }

        return account;
    }
    
}
