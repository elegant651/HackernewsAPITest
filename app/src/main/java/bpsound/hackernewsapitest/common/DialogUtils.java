package bpsound.hackernewsapitest.common;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by elegantuniv on 2017. 6. 19..
 */

public class DialogUtils {
    private static ProgressDialog mProgDlog;

    public static void showLoadingProgDialog(Context context) {
        mProgDlog = ProgressDialog.show(context, "", "Loading...", true);
    }

    public static void hideLoadingProgDialog() {
        if(mProgDlog!=null && mProgDlog.isShowing()){
            mProgDlog.hide();
        }
    }

    public static void setAlertDialog(Context context, String msg) {
        new AlertDialog.Builder(context).setTitle("Notify").setMessage(msg)
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    }
                }).show();
    }

    public static void setAlertDialog(Context context, String title,
                                      String msg, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(msg)
                .setPositiveButton("Confirm", onClickListener).show();
    }
}
