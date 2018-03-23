package com.trekplanner.app.fragment.editable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;

public abstract class EditFragment extends Fragment implements View.OnClickListener {

    DbHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(getLayout(), container, false);
        FloatingActionButton fab = view.findViewById(R.id.editview_floating_action_btn);
        fab.setOnClickListener(getActionButtonOnClickListener());

        buildView(view);

        return view;
    }

    protected abstract void buildView(View view);

    protected abstract int getLayout();

    protected View.OnClickListener getActionButtonOnClickListener() {
        return this;
    }
}
