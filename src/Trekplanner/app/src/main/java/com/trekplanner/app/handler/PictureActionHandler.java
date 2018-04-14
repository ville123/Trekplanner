package com.trekplanner.app.handler;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;

import com.trekplanner.app.utils.AppUtils;

public class PictureActionHandler implements DialogInterface.OnClickListener {

    private final Fragment fragment;

    public PictureActionHandler(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int rowIndex) {

        switch (rowIndex) {

            case 0:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                this.fragment.startActivityForResult(intent, AppUtils.REQUEST_IMAGE_CAPTURE);
                break;
            case 1:
                Snackbar.make(this.fragment.getActivity().findViewById(android.R.id.content), "TODO: toteuta galleriasta haku", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                break;
            default:
        }

    }
}
