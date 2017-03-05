package test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/6/24.
 */
public class operationReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {

        String url = intent.getStringExtra("url");

        if (Constants.mListener != null) {
            Constants.mListener.onActivityFinished();
            Toast.makeText(context,"notnull", 0).show();
        }else{
            Toast.makeText(context,url, 0).show();
        }
        /*
        Intent Service= new Intent();


      Service.putExtra("url", url);*/
       /* Service.setAction("com.example.admin.wifiserivce");
        Service.setPackage(context.getPackageName());
        context.startService(Service);*/
    }

}
