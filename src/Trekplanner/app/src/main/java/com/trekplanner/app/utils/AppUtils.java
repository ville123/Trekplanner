package com.trekplanner.app.utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.trekplanner.app.R;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
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

    public static final String DATETIME_FORMAT_DB_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_FORMAT_ONLY_DATE = "dd.MM.yyyy";
    public static final String DATETIME_FORMAT_DATE_TIME = "dd.MM.yyyy HH:mm";
    public static final String ITEM_TYPE_KEY = "item_type_key";
    public static final Integer REQUEST_IMAGE_CAPTURE = 1;


    // can implement some small nice functions to handle routine tasks
    // these are just for example:

    public static Bitmap decodeToBitmap(String pic) {
        byte[] bytes = Base64.decode(pic, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public static String encodeToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return Base64.encodeToString(byteArrayOutputStream .toByteArray(), Base64.DEFAULT);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String formatDateTime(String datetime, String pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATETIME_FORMAT_DATE_TIME);
        SimpleDateFormat reFormatter = new SimpleDateFormat(pattern);
        try {
            Date date = formatter.parse(datetime);
            return reFormatter.format(date);
        } catch (ParseException e) {
            Log.w("TREK_AppUtils", "Exception in formatting string " + datetime + ", " + e.getMessage());
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

    public static void showSelectionDialog(Activity activity,
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

    public static void showItemTypeSelectionPopup(View view, Activity activity, int menuId, PopupMenu.OnMenuItemClickListener listener) {
        PopupMenu popup = new PopupMenu(activity, view);
        try {
            Field[] fields = popup.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popup);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        popup.getMenuInflater().inflate(menuId, popup.getMenu());
        popup.setOnMenuItemClickListener(listener);
        popup.show();
    }

    public static void showOkMessage(View view, int message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public static void closeInputwidget(Activity activity, View view) {
        View focusView = activity.getCurrentFocus();
        if (focusView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static int getSelectionIndex(Collection<String> values, String value) {
        int i = 0;
        for (String val : values) {
            if (val.equals(value)) {
                return i;
            }
            i++;
        }
        return -1; // not found
    }

    public static View.OnClickListener getDatePickerListener(final Activity activity, final EditText field) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                field.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);

                            }
                        }, mYear, mMonth, mDay);

                datePickerDialog.show();
            }
        };
    }

    public static View.OnClickListener getTimePickerListener(final Activity activity, final EditText field) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(activity,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                field.setText(field.getText().toString() + " " + hourOfDay + ":" + String.format("%02d", minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        };
    }

    public abstract static class EditTextOkListener {
        public abstract void onOk(String text);
    }
}
