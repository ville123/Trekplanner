package com.trekplanner.app.fragment.editable;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.handler.PictureActionHandler;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.utils.AppUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sami
 *
 * Edit fragment for Treks
 */
public class TrekEditFragment extends EditFragment {

    private Trek trek;
    private static Map<String,String> levelOptionMap;

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

        AppUtils.closeInputwidget(getActivity(),parentView);

        EditText startField = parentView.findViewById(R.id.editview_trek_start_fld);
        this.trek.setStart(startField.getText().toString());

        EditText descField = parentView.findViewById(R.id.editview_trek_description_fld);
        this.trek.setDescription(descField.getText().toString());

        EditText endField = parentView.findViewById(R.id.editview_trek_end_fld);
        this.trek.setEnd(endField.getText().toString());

        TextView notesField = parentView.findViewById(R.id.editview_trek_notes_fld);
        this.trek.setNotes(notesField.getText().toString());

        TextView lessonField = parentView.findViewById(R.id.editview_trek_lesson_fld);
        this.trek.setLessonsLearned(lessonField.getText().toString());

        Spinner levelSpinner = parentView.findViewById(R.id.spinnerTrekLevel);
        this.trek.setLevel((String)levelOptionMap.keySet().toArray()[levelSpinner.getSelectedItemPosition()]);

        EditText startCoordsField = parentView.findViewById(R.id.editview_trek_start_coord_fld);
        this.trek.setStartCoords(startCoordsField.getText().toString());

        EditText endCoordsField = parentView.findViewById(R.id.editview_trek_end_coord_fld);
        this.trek.setEndCoords(endCoordsField.getText().toString());

        EditText lengthField = parentView.findViewById(R.id.editview_trek_length_fld);
        if (!lengthField.getText().toString().isEmpty())
            this.trek.setLength(Double.valueOf(lengthField.getText().toString()));
//        String lengthString = lengthField.getText().toString().trim();
//        Double length = Double.parseDouble(lengthString);
//        this.trek.setLength(length);

        if(TextUtils.isEmpty(descField.getText())) {
            descField.setError("Anna retken nimi plz");
        } else {
            db.saveTrek(this.trek);
            AppUtils.showOkMessage(view, R.string.phrase_save_success);
        }

    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_TrekEditFragment", "Building TrekEdit view");

        if (levelOptionMap == null) {
            levelOptionMap = new HashMap<>();
            List<String> levelOptionList = Arrays.asList(getResources().getStringArray(R.array.array_trek_level));
            List<String> levelEnumList = Arrays.asList(getResources().getStringArray(R.array.array_trek_level_enums));
            for (int i = 0; i < levelEnumList.size(); i++) {
                levelOptionMap.put(levelEnumList.get(i), levelOptionList.get(i));
            }
        }

        Spinner levelSpinner = view.findViewById(R.id.spinnerTrekLevel);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, new ArrayList<>(levelOptionMap.values()));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        levelSpinner.setAdapter(adapter);

        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (trek != null)
                    trek.setLevel((String) levelOptionMap.keySet().toArray()[i]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        /* tehdään tähän date-time-picker retken alku- ja loppu ajankohdan muokkausta varten */
        ImageView btnDatePickerStart = view.findViewById(R.id.editview_trek_start_select_date_button);
        ImageView btnDatePickerEnd = view.findViewById(R.id.editview_trek_end_select_date_button);
        ImageView btnTimePickerStart = view.findViewById(R.id.editview_trek_start_select_time_button);
        ImageView btnTimePickerEnd = view.findViewById(R.id.editview_trek_end_select_time_button);
        final EditText startField = view.findViewById(R.id.editview_trek_start_fld);
        final EditText endField = view.findViewById(R.id.editview_trek_end_fld);

        btnDatePickerStart.setOnClickListener(AppUtils.getDatePickerListener(this.getActivity(), startField));
        btnTimePickerStart.setOnClickListener(AppUtils.getTimePickerListener(this.getActivity(), startField));
        btnDatePickerEnd.setOnClickListener(AppUtils.getDatePickerListener(this.getActivity(), endField));
        btnTimePickerEnd.setOnClickListener(AppUtils.getTimePickerListener(this.getActivity(), endField));

        if (this.trek == null) {
            this.trek = new Trek();
        } else {
            EditText descField = view.findViewById(R.id.editview_trek_description_fld);
            EditText startCoordsField = view.findViewById(R.id.editview_trek_start_coord_fld);
            EditText endCoordsField = view.findViewById(R.id.editview_trek_end_coord_fld);
            TextView notesField = view.findViewById(R.id.editview_trek_notes_fld);
            AppUtils.buildEditorForNotes(this.getActivity(), notesField);
            TextView lessonField = view.findViewById(R.id.editview_trek_lesson_fld);
            AppUtils.buildEditorForNotes(this.getActivity(), lessonField);
            EditText lengthField = view.findViewById(R.id.editview_trek_length_fld);

            levelSpinner.setSelection(AppUtils.getSelectionIndex(levelOptionMap.keySet(),trek.getLevel()));

            descField.setText(trek.getDescription());
            lengthField.setText(trek.getLength().toString().trim());
            notesField.setText(trek.getNotes());
            lessonField.setText(trek.getLessonsLearned());
            startCoordsField.setText(trek.getStartCoords());
            endCoordsField.setText(trek.getEndCoords());
            startField.setText(trek.getStart());
            endField.setText(trek.getEnd());
        }

        // get the treks pic for header background
        if (trek.getPic() != null && !trek.getPic().isEmpty()) {
            ImageView hdrImage = getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_picture);
            hdrImage.setImageBitmap(AppUtils.decodeToBitmap(trek.getPic()));
        }

        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_trek) + " - " + trek.getDescription());

        /** camera button and picture **/
        ImageView fab = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_camera_button);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AppUtils.showSelectionDialog(
                        getActivity(),
                        null,
                        R.array.image_action_choices,
                        pictureActionHandler);
            }
        });

        /** camera button and picture end **/
    }

    // handle image capture from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // parametreinä requestcode ja resultcode jotta voidaan varmistaa menikö kuvan otto ok
        if(requestCode == AppUtils.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap picMap = (Bitmap) extras.get("data");
            this.trek.setPic(AppUtils.encodeToString(picMap));
            if (this.trek.getId() != null && !this.trek.getId().isEmpty()) db.saveTrek(this.trek);
            ImageView hdrImage = getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_picture);
            hdrImage.setImageBitmap(picMap);

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_trek_content_layout;
    }
}
