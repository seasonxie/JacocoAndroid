package test;

/**
 * Created by Administrator on 2016/12/10.
 */
import android.util.Log;

import com.jacs.zhaotang.jscscs.MainActivity;


public class InstrumentedActivity extends MainActivity {
    public static String TAG = "InstrumentedActivity";

    private FinishListener mListener;

    public void setFinishListener(FinishListener listener) {

        mListener = listener;
        Constants.mListener=listener;
    }


    @Override
    public void onDestroy() {
        Log.d(TAG + ".InstrumentedActivity", "onDestroy()");
        super.finish();
       /* if (mListener != null) {
            mListener.onActivityFinished();
        }*/
    }

}