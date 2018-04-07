package com.trekplanner.app.fragment.editable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageButton;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.utils.AppUtils;

import static android.app.Activity.RESULT_OK;

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

        EditText descField = parentView.findViewById(R.id.editview_trek_description_fld);
        this.trek.setDescription(descField.getText().toString());

        EditText endField = parentView.findViewById(R.id.editview_trek_end_fld);
        this.trek.setEnd(endField.getText().toString());

        EditText notesField = parentView.findViewById(R.id.editview_trek_notes_fld);
        this.trek.setNotes(notesField.getText().toString());

        EditText lessonField = parentView.findViewById(R.id.editview_trek_lesson_fld);
        this.trek.setLessonsLearned(lessonField.getText().toString());

        EditText levelField = parentView.findViewById(R.id.editview_trek_level_fld);
        this.trek.setLevel(levelField.getText().toString());

        EditText startCoordsField = parentView.findViewById(R.id.editview_trek_start_coord_fld);
        this.trek.setStartCoords(startCoordsField.getText().toString());

        EditText endCoordsField = parentView.findViewById(R.id.editview_trek_end_coord_fld);
        this.trek.setEndCoords(endCoordsField.getText().toString());

        EditText lengthField = parentView.findViewById(R.id.editview_trek_length_fld);
        String lengthString = lengthField.getText().toString().trim();
        Double length = Double.parseDouble(lengthString);
        this.trek.setLength(length);

        db.saveTrek(this.trek);

        AppUtils.showOkMessage(view, R.string.phrase_save_success);
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

        EditText startCoordsField = view.findViewById(R.id.editview_trek_start_coord_fld);
        startCoordsField.setText(trek.getStartCoords());

        EditText endCoordsField = view.findViewById(R.id.editview_trek_end_coord_fld);
        endCoordsField.setText(trek.getEndCoords());

        EditText notesField = view.findViewById(R.id.editview_trek_notes_fld);
        notesField.setText(trek.getNotes());

        EditText lessonField = view.findViewById(R.id.editview_trek_lesson_fld);
        lessonField.setText(trek.getLessonsLearned());

        EditText levelField = view.findViewById(R.id.editview_trek_level_fld);
        levelField.setText(trek.getLevel());

        EditText lengthField = view.findViewById(R.id.editview_trek_length_fld);
        lengthField.setText(trek.getLength().toString().trim());

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.editview_floating_camera_btn);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_trek) + " - " + trek.getDescription());

        /** camera button and picture **/
        ImageButton fab = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_camera_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, AppUtils.REQUEST_IMAGE_CAPTURE);
            }
        });

        /** camera button and picture end **/
    }

    // handle image capture from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // parametreinä requestcode ja resultcode jotta voidaan varmistaa menikö kuvan otto ok
        if(requestCode == AppUtils.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap picMap = (Bitmap) extras.get("data");
            this.trek.setPic(AppUtils.encodeToString(picMap));
            if (this.trek.getId() != null && !this.trek.getId().isEmpty()) db.saveTrek(this.trek);
            View headerLayout = getActivity().findViewById(android.R.id.content).findViewById(R.id.header_layout);
            headerLayout.setBackground(new BitmapDrawable(getResources(), picMap));

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_trek_content_layout;
    }

    // TODO: poista tämä ja siirrä buildView -metodiin
    public void setHeaderPic(Resources resources, View headerLayout) {
        if (trek.getPic() != null && !trek.getPic().isEmpty()) {
            headerLayout.setBackground(new BitmapDrawable(resources, AppUtils.decodeToBitmap(trek.getPic())));
        }
    }
}
