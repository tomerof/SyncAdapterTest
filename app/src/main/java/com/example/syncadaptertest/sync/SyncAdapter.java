package com.example.syncadaptertest.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
    }

    public static void syncImmediately(Context context){
        Log.d("Sync", "Sync Immediately Fire");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED,true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        Account account = getSyncAccount(context);
        ContentResolver.setIsSyncable(account,context.getString(R.string.content_authority),1);
        ContentResolver.requestSync(
                account,
                context.getString(R.string.content_authority),
                bundle
        );
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
