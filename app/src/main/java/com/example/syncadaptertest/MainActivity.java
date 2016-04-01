package com.example.syncadaptertest;

import android.app.Notification;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.syncadaptertest.sync.SyncAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button syncBtn = (Button)findViewById(R.id.sync_btn);

        assert syncBtn != null;
        syncBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SyncAdapter.syncImmediately(getApplicationContext());
            }
        });
    }
}
