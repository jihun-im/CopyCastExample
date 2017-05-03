package com.example.jihun.copycastexample;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.jihun.copycastexample.tools.CopyCastUtils;

/**
 * Created by Jihun on 2017-02-04.
 */

public class CopyCastService extends Service {


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String TAG = "CopyCast";

        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {

                ClipData clipData;
                ClipDescription clipDescription;
                String clipString;
                ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                if (!cm.hasPrimaryClip()) {
                    Log.i(TAG, "hasPrimaryClip==false");
                    return;
                }

                clipData = cm.getPrimaryClip();
                clipDescription = cm.getPrimaryClipDescription();
                clipString = clipData.getItemAt(0).getText().toString();

                //Toast.makeText(CopyCastService.this.getApplicationContext(), clipData.getItemAt(0).getText(), Toast.LENGTH_SHORT).show();

//                Log.i(TAG, "clipData.getItemAt(0).getHtmlText() : " + clipData.getItemAt(0).getHtmlText());
//                Log.i(TAG, "clipData.getItemAt(0).getUri() : " + clipData.getItemAt(0).getUri());
//                Log.i(TAG, "clipDescription.getMimeType(0) : " + clipDescription.getMimeType(0));
//                Log.i(TAG, "clipString: " + clipString);
//                Log.i(TAG, "loadClipString: " + loadClipString());
//                Log.i(TAG, "clipDescription: " + clipDescription.toString());
//                Log.i(TAG, "isUrl : " + CopyCastUtils.isUrl(clipString));

//                popUpDialog(clipString, loadClipString() );

                CopyCastUtils.saveClipString(CopyCastService.this.getApplicationContext(), clipString);
            }

//            private void popUpDialog(String clipString) {
//                popUpDialog(clipString, null);
//            }
//
//            private void popUpDialog(String clipString, String formerClipString) {
//                Intent requestDialog = new Intent(getApplicationContext(), DialogActivity.class);
//                if (formerClipString != null) {
//                    Log.i(TAG, "hit 1");
//                    requestDialog.putExtra("clipString", formerClipString + "\n" + clipString);
//                } else {
//                    Log.i(TAG, "hit 2");
//                    requestDialog.putExtra("clipString", clipString);
//                }
//
//                requestDialog.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(requestDialog);
//            }

        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
