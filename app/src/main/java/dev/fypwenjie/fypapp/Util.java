package dev.fypwenjie.fypapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by VINTEDGE on 7/4/2018.
 */

public class Util {
    public static void shortToast(Context a, String message) {
        Toast.makeText(a, message, Toast.LENGTH_SHORT).show();
    }

    public static void longToast(Context a, String message) {
        Toast.makeText(a, message, Toast.LENGTH_LONG).show();
    }
}
