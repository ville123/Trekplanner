package com.trekplanner.app.fragment.editable;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.handler.PictureActionHandler;

/**
 * Created by Sami
 *
 * Base Class for all edit fragments.
 * Adds floating action buttons for the view
 */
public abstract class EditFragment extends DialogFragment implements View.OnClickListener {

    DbHelper db;
    protected PictureActionHandler pictureActionHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(getLayout(), container, false);
        FloatingActionButton fab = view.findViewById(R.id.editview_floating_action_btn);
        fab.setOnClickListener(getActionButtonOnClickListener());

        // show camerabutton
        ImageView camBtn = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_camera_button);
        camBtn.setVisibility(View.VISIBLE);

        this.pictureActionHandler = new PictureActionHandler(this);

        buildView(view);

        return view;
    }

    protected abstract void buildView(View view);

    protected abstract int getLayout();

    protected View.OnClickListener getActionButtonOnClickListener() {
        return this;
    }
}
