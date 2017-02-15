package com.superw.androidproject.util;

import android.content.Context;
import android.widget.Toast;

public class TShow {

    private static Toast toast;


    public static void shortTime(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public static void shortTime(Context context, int text) {
        shortTime(context, context.getResources().getString(text));
    }

    public static void longTime(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }

    public static void longTime(Context context, int text) {
        longTime(context, context.getResources().getString(text));
    }

    public static void singleShortToast(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void singleShortToast(Context context, int text) {
        singleShortToast(context, context.getResources().getString(text));
    }


    public static void singleLongToast(Context context, CharSequence text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        toast.show();
    }

    public static void singleLongToast(Context context, int text) {
        singleLongToast(context, context.getResources().getString(text));
    }
}
