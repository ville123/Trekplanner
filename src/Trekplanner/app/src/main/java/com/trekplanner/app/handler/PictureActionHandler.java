package com.trekplanner.app.handler;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;

import com.trekplanner.app.utils.AppUtils;

public class PictureActionHandler implements DialogInterface.OnClickListener {

    private final Activity activity;

    public PictureActionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int rowIndex) {

        switch (rowIndex) {

            case 0:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.activity.startActivityForResult(intent, AppUtils.REQUEST_IMAGE_CAPTURE);
                break;
            case 1:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "TODO: toteuta galleriasta haku", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
        }

    }
}
