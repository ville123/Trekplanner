package com.trekplanner.app.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.trekplanner.app.R;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Sami
 *
 * Utility class for the app
 */
public class AppUtils {

    public static final Integer ITEM_LIST_ACTION_ID = 1;
    public static final Integer TREK_LIST_ACTION_ID = 2;
    public static final Integer TREKITEM_LIST_ACTION_ID = 2;
    public static final Integer SORT_ORDER_BY_NAME = 1;
    public static final Integer SORT_ORDER_BY_TYPE = 2;

    public static final String DATETIME_FORMAT_DB_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_ONLY_DATE = "dd.MM.yyyy";
    public static final String DATETIME_FORMAT_DATE_TIME = "dd.MM.yyyy HH:mm:ss";


    // can implement some small nice functions to handle routine tasks
    // these are just for example:

    public static Date getDate(String dateTimeString) {return null;}
    public static Time getTime(String dateTimeString) {return null;}

    public static Bitmap decodeToBitmap(String pic) {
        byte[] bytes = Base64.decode(pic, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String formatDateTime(String datetime, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT_DB_FORMAT);
        SimpleDateFormat reFormatter = new SimpleDateFormat(pattern);
        try {
            Date date = formatter.parse(datetime);
            return reFormatter.format(date);
        } catch (ParseException e) {
            Log.w("TREK_AppUtils", "Exception in formatting string " + datetime + ", " + e.getMessage());
            e.printStackTrace();
            return datetime;
        }
    }

    public static void showConfirmDialog(Activity activity,
                                         int messageResouce,
                                         DialogInterface.OnClickListener yesListener,
                                         DialogInterface.OnClickListener noListener) {

        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.phrase_notification))
                .setMessage(activity.getResources().getString(messageResouce))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, yesListener)
                .setNegativeButton(android.R.string.no, noListener)
                .show();
    }

    public static void showSelectionDialog(Activity activity, int messageResouce,
                                           DialogInterface.OnClickListener cancelListener,
                                           int itemArray,
                                           DialogInterface.OnClickListener selectionListener) {

        new AlertDialog.Builder(activity)
                .setTitle(activity.getResources().getString(R.string.phrase_select))
                .setIcon(android.R.drawable.ic_dialog_info)
                .setNegativeButton(android.R.string.no, cancelListener)
                .setItems(itemArray, selectionListener)
                .show();
    }

    public static void showOkMessage(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
