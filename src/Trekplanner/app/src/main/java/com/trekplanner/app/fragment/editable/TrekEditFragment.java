package com.trekplanner.app.fragment.editable;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Trek;

/**
 * Created by Sami
 *
 * Edit fragment for Treks
 */
public class TrekEditFragment extends EditFragment {

    private Trek trek;

    public static TrekEditFragment getNewInstance(DbHelper db, Trek trek) {
        Log.d("TREK_TrekEditFragment", "New TrekEditFragment -instance created");
        TrekEditFragment instance = new TrekEditFragment();
        instance.db = db;
        instance.trek = trek;
        return instance;
    }

    @Override
    public void onClick(View view) {
        Log.d("TREK_TrekEditFragment", "Save button clicked");
        CoordinatorLayout parentView = (CoordinatorLayout) view.getParent();
        EditText startField = parentView.findViewById(R.id.editview_trek_start_fld);
        this.trek.setStart(startField.getText().toString());

        // TODO: add all fields

        db.saveTrek(this.trek);

        Snackbar.make(view, R.string.phrase_save_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_TrekEditFragment", "Building TrekEdit view");

        EditText descField = view.findViewById(R.id.editview_trek_description_fld);
        descField.setText(trek.getDescription());

        EditText startField = view.findViewById(R.id.editview_trek_start_fld);
        startField.setText(trek.getStart());

        EditText endField = view.findViewById(R.id.editview_trek_end_fld);
        endField.setText(trek.getEnd());

        EditText notesField = view.findViewById(R.id.editview_trek_notes_fld);
        notesField.setText(trek.getNotes());
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_trek_content_layout;
    }
}
