package com.trekplanner.app.fragment.editable;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import android.widget.Spinner;
import android.widget.TimePicker;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.utils.AppUtils;

import java.sql.Array;
import java.sql.Struct;
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

        EditText notesField = parentView.findViewById(R.id.editview_trek_notes_fld);
        this.trek.setNotes(notesField.getText().toString());

        EditText lessonField = parentView.findViewById(R.id.editview_trek_lesson_fld);
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

        // TODO: add all fields

        if(TextUtils.isEmpty(descField.getText())) {
            descField.setError("Anna retken nimi plz");
        } else {
            db.saveTrek(this.trek);

        Snackbar.make(view, R.string.phrase_save_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
        ImageButton btnDatePickerStart = view.findViewById(R.id.editview_trek_start_select_date_button);
        ImageButton btnDatePickerEnd = view.findViewById(R.id.editview_trek_end_select_date_button);
        ImageButton btnTimePickerStart = view.findViewById(R.id.editview_trek_start_select_time_button);
        ImageButton btnTimePickerEnd = view.findViewById(R.id.editview_trek_end_select_time_button);
        final EditText startField = view.findViewById(R.id.editview_trek_start_fld);
        final EditText endField = view.findViewById(R.id.editview_trek_end_fld);

        btnDatePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        startField.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                    }
                }, mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        btnTimePickerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        startField.setText(startField.getText().toString() + " " + hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });

        btnDatePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        endField.setText(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });

        btnTimePickerEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        endField.setText(endField.getText().toString() + " " + hourOfDay + ":" + minute);
                    }
                },mHour,mMinute,true);
                timePickerDialog.show();
            }
        });

        if (this.trek == null) {
            this.trek = new Trek();
        } else {
            EditText descField = view.findViewById(R.id.editview_trek_description_fld);
            EditText startCoordsField = view.findViewById(R.id.editview_trek_start_coord_fld);
            EditText endCoordsField = view.findViewById(R.id.editview_trek_end_coord_fld);
            EditText notesField = view.findViewById(R.id.editview_trek_notes_fld);
            EditText lessonField = view.findViewById(R.id.editview_trek_lesson_fld);
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

//        EditText descField = view.findViewById(R.id.editview_trek_description_fld);
//        descField.setText(trek.getDescription());

//        EditText startCoordsField = view.findViewById(R.id.editview_trek_start_coord_fld);
//        startCoordsField.setText(trek.getStartCoords());

//        EditText endCoordsField = view.findViewById(R.id.editview_trek_end_coord_fld);
//        endCoordsField.setText(trek.getEndCoords());

//        EditText notesField = view.findViewById(R.id.editview_trek_notes_fld);
//        notesField.setText(trek.getNotes());

//        EditText lessonField = view.findViewById(R.id.editview_trek_lesson_fld);
//        lessonField.setText(trek.getLessonsLearned());

//        EditText lengthField = view.findViewById(R.id.editview_trek_length_fld);
//        lengthField.setText(trek.getLength().toString().trim());

        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.editview_floating_camera_btn);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_trek) + " - " + trek.getDescription());
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_trek_content_layout;
    }

    public void setHeaderPic(Resources resources, View headerLayout) {
        if (trek.getPic() != null && !trek.getPic().isEmpty()) {
            headerLayout.setBackground(new BitmapDrawable(resources, AppUtils.decodeToBitmap(trek.getPic())));
        }
    }
}
