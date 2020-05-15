package com.example.pagedemo.utils;

import android.app.Activity;
import android.widget.Toast;

public class EasyToast {
    public static void showText(final Activity activity, final CharSequence text, final int duration) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, text, duration).show();
            }
        });
    }
}
