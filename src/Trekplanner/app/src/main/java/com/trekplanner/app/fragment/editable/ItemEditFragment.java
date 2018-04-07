package com.trekplanner.app.fragment.editable;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.trekplanner.app.R;
import com.trekplanner.app.activity.MainActivity;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sami
 *
 * Edit fragment for Items
 */
public class ItemEditFragment extends EditFragment {

    private Item item;
    private static Map<String, String> typeOptionMap;


    public static ItemEditFragment getNewInstance(DbHelper db, Item item) {
        Log.d("TREK_ItemEditFragment", "New TrekEditFragment -instance created");

        // cant use singelton -pattern for item edit page since context (item) is changing
        ItemEditFragment instance = new ItemEditFragment();
        instance.db = db;
        instance.item = item;
        return instance;
    }

    @Override
    public void onClick(View view) {
        Log.d("TREK_ItemEditFragment", "Save button clicked");

        CoordinatorLayout parentView = (CoordinatorLayout) view.getParent();

        // first close the keyboard
        AppUtils.closeInputwidget(getActivity(), parentView);

        EditText mWeight = parentView.findViewById(R.id.edit_text_weight);
        EditText mName = parentView.findViewById(R.id.edit_text_name);
        EditText mNotes = parentView.findViewById(R.id.text_edit_notes_edit);
        EditText mEnergy = parentView.findViewById(R.id.edit_text_energy);
        EditText mProtein = parentView.findViewById(R.id.edit_text_protein);
        EditText mDeadline = parentView.findViewById(R.id.edit_text_deadline);

        if (!mWeight.getText().toString().isEmpty())
            this.item.setWeight(Double.valueOf(mWeight.getText().toString()));

        this.item.setName(mName.getText().toString());
        this.item.setNotes(mNotes.getText().toString());

        if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype3))) {
            if (!mEnergy.getText().toString().isEmpty())
                this.item.setEnergy(Double.valueOf(mEnergy.getText().toString()));

            if (!mProtein.getText().toString().isEmpty())
                this.item.setProtein(Double.valueOf(mProtein.getText().toString()));
        }

        if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype5)) ||
                this.item.getType().equals(getResources().getString(R.string.enum_itemtype7))) {
            this.item.setDeadline(mDeadline.getText().toString());
        }

        if(TextUtils.isEmpty(mName.getText())) {
            mName.setError(getResources().getString(R.string.phrase_name_required));
        } else {
            Log.d("TREK_is_default", "item is default in save: " + item.isDefault());
            db.saveItem(this.item);

            AppUtils.showOkMessage(view, R.string.phrase_save_success);
        }
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_ItemEditFragment", "Building TrekEdit view");

        // setting page header content
        ImageView headerImageView
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_image);
        headerImageView.setImageResource(R.drawable.item);

        // hide actions from header
        TextView action1View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action1);
        action1View.setText("");

        TextView action2View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action2);
        action2View.setText("");

        //Actionbar content
        ((AppCompatActivity) this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_item));

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

        //populate type and status key-values
        if (typeOptionMap == null) {
            typeOptionMap = new HashMap<>();
            List<String> typeOptionList = Arrays.asList(getResources().getStringArray(R.array.array_type_options));
            List<String> typeEnumList = Arrays.asList(getResources().getStringArray(R.array.array_type_enums));
            for (int i = 0; i < typeEnumList.size(); i++) {
                typeOptionMap.put(
                        typeEnumList.get(i), typeOptionList.get(i)
                );
            }
        }

        // handle Item insert/update
        // first get all UI views

        /** spinners **/
        Spinner typeSpinner = view.findViewById(R.id.spinner_type);
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, new ArrayList<>(typeOptionMap.values()));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(dataAdapter);
        typeSpinner.setSelection(AppUtils.getSelectionIndex(typeOptionMap.keySet(), item.getType()));
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (item != null)
                    item.setType((String) typeOptionMap.keySet().toArray()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        /** spinners end **/

        /** date time pickers **/

        ImageButton btnDatePicker = view.findViewById(R.id.editview_select_date_button);
        ImageButton btnTimePicker = view.findViewById(R.id.editview_select_time_button);
        final EditText mDeadline = view.findViewById(R.id.edit_text_deadline);

        if (btnDatePicker != null) {
            btnDatePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // Get Current Date
                    final Calendar c = Calendar.getInstance();
                    int mYear = c.get(Calendar.YEAR);
                    int mMonth = c.get(Calendar.MONTH);
                    int mDay = c.get(Calendar.DAY_OF_MONTH);


                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    mDeadline.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                                }
                            }, mYear, mMonth, mDay);

                    datePickerDialog.show();
                }
            });
            btnTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Get Current Time
                    final Calendar c = Calendar.getInstance();
                    int mHour = c.get(Calendar.HOUR_OF_DAY);
                    int mMinute = c.get(Calendar.MINUTE);

                    // Launch Time Picker Dialog
                    TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                            new TimePickerDialog.OnTimeSetListener() {

                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay,
                                                      int minute) {

                                    mDeadline.setText(mDeadline.getText().toString() + " " + hourOfDay + ":" + minute);
                                }
                            }, mHour, mMinute, true);
                    timePickerDialog.show();
                }
            });
        }

        /** date time pickers end **/

        /** checkbox **/

        CheckBox isDefCheckBox = view.findViewById(R.id.is_default_checkbox);

        if (isDefCheckBox!=null) {
            isDefCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    item.setDefault(isChecked);
                }
            });
        }

        /** checkbox end **/

        if (this.item.getId() != null && !this.item.getId().isEmpty()) {

            // update item
            EditText mName = view.findViewById(R.id.edit_text_name);
            EditText mNotes = view.findViewById(R.id.text_edit_notes_edit);

            EditText mWeight = view.findViewById(R.id.edit_text_weight);
            if (mWeight!= null && item.getWeight() != null)
                mWeight.setText(String.valueOf(item.getWeight()));

            EditText mEnergy = view.findViewById(R.id.edit_text_energy);
            EditText mProtein = view.findViewById(R.id.edit_text_protein);
            if (mEnergy!= null && item.getEnergy() != null)
                mEnergy.setText(String.valueOf(item.getEnergy()));
            if (mProtein != null && item.getProtein() != null)
                mProtein.setText(String.valueOf(item.getProtein()));

            mName.setText(item.getName());
            mNotes.setText(item.getNotes());

            if (mDeadline!=null) mDeadline.setText(item.getDeadline());
            isDefCheckBox.setChecked(item.isDefault());

            if (item.getPic() != null && !item.getPic().isEmpty()) {
                View headerLayout = getActivity().findViewById(android.R.id.content).findViewById(R.id.header_layout);
                headerLayout.setBackground(new BitmapDrawable(getResources(), AppUtils.decodeToBitmap(item.getPic())));
            }
        }

    }

    // handle image capture from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // parametreinä requestcode ja resultcode jotta voidaan varmistaa menikö kuvan otto ok
        if(requestCode == AppUtils.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap picMap = (Bitmap) extras.get("data");
            this.item.setPic(AppUtils.encodeToString(picMap));
            if (this.item.getId() != null && !this.item.getId().isEmpty()) db.saveItem(this.item);
            View headerLayout = getActivity().findViewById(android.R.id.content).findViewById(R.id.header_layout);
            headerLayout.setBackground(new BitmapDrawable(getResources(), picMap));

        }
        super.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected int getLayout() {

        if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype1)) ||
                this.item.getType().equals(getResources().getString(R.string.enum_itemtype2)) ||
                this.item.getType().equals(getResources().getString(R.string.enum_itemtype8))) {
            return R.layout.editview_item_equipment_layout;
        } else if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype5)) ||
                this.item.getType().equals(getResources().getString(R.string.enum_itemtype7))) {
            return R.layout.editview_item_reminder_layout;
        } else if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype3))) {
            return R.layout.editview_item_food_layout;
        } else {
            // item types 4 and 6
            return R.layout.editview_item_idea_layout;
        }

    }

}
