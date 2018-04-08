package com.trekplanner.app.handler;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;

public class ImportTemplateActionHandler implements DialogInterface.OnClickListener {

    private final Activity activity;

    public ImportTemplateActionHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int rowIndex) {

        // TODO: implement

        switch (rowIndex) {

            case 0:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "Päväretki valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case 1:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "Yön yli -retki valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case 2:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "Pidempi retki valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            case 3:
                Snackbar.make(this.activity.findViewById(android.R.id.content), "Talviretki valittu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
        }

    }
}
