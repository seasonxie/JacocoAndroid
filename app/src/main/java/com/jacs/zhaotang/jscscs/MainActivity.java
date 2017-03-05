package com.jacs.zhaotang.jscscs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {
    private static Button goButton = null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton = (Button) findViewById(R.id.button1);

    }

}

class ClickListener implements View.OnClickListener {
    ClickListener() {
    }

    public void onClick(View v) {
        int id = v.getId();
        if (R.id.button1 == id) {
            Log.i("tt'", id + "  "+v.getTextDirection());
            if(v.getTextDirection()==0){
                Log.i("tt'", id + "  "+v.getTextDirection());
            }else{
                Log.i("tt'", id + "  "+v.getTextAlignment());
            }
        }
    }
}
