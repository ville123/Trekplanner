package com.trekplanner.app.handler;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;

public class ExportActionHandler implements DialogInterface.OnClickListener {

    private final Activity activity;

    public ExportActionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {

        // TODO: implement saving the items to json file in selected filesystem
        // TODO: only 0 = phone is implemented! no 1 and 2.
        // which:
        // 0= filesystem (phone): to public filesystem!
        // 1= GDrive
        // 2= OneDrive

        switch (which) {

            case 0:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "TODO: toteuta json-filun tallennus puhelimeen (public)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case 1:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "GDrive valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case 2:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "OneDrive valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
        }

    }
}
