package com.example.jihun.copycastexample.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.jihun.copycastexample.CopyCastService;

/**
 * Created by Jihun on 2017-02-05.
 */

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent requestService = new Intent(context, CopyCastService.class);
            context.startService(requestService);
        }
    }
}