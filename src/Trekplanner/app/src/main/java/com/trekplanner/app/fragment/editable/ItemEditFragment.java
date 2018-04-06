package com.trekplanner.app.fragment.editable;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


import com.trekplanner.app.R;
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

        Spinner typeSpinner = parentView.findViewById(R.id.spinner_type);
        EditText mWeight = parentView.findViewById(R.id.edit_text_weight);
        EditText mName = parentView.findViewById(R.id.edit_text_name);
        EditText mNotes = parentView.findViewById(R.id.text_edit_notes_edit);
        //EditText mPics = parentView.findViewById(R.id.edit_text_pic_edit);
        EditText mEnergy = parentView.findViewById(R.id.edit_text_energy);
        EditText mProtein = parentView.findViewById(R.id.edit_text_protein);
        EditText mDeadline = parentView.findViewById(R.id.edit_text_deadline);
        CheckBox isDefCheckBox = parentView.findViewById(R.id.is_default_checkbox);

        // validate input
        if (TextUtils.isEmpty(mName.getText())) {
            mName.setError(getResources().getString(R.string.phrase_name_required));
            return;
        }

        this.item.setType((String)typeOptionMap.keySet().toArray()[typeSpinner.getSelectedItemPosition()]);
        this.item.setDefault(isDefCheckBox.isSelected());

        if (!mWeight.getText().toString().isEmpty())
            this.item.setWeight(Double.valueOf(mWeight.getText().toString()));

        this.item.setName(mName.getText().toString());
        this.item.setNotes(mNotes.getText().toString());
        //this.item.setPic(mPics.getText().toString());

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

        db.saveItem(this.item);

        Snackbar.make(view, R.string.phrase_save_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_item));

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
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (item != null)
                    item.setType((String)typeOptionMap.keySet().toArray()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        /** spinners end **/

        /** date time pickers **/

        ImageButton btnDatePicker= view.findViewById(R.id.editview_select_date_button);
        ImageButton btnTimePicker= view.findViewById(R.id.editview_select_time_button);
        final EditText mDeadline = view.findViewById(R.id.edit_text_deadline);

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

        /** date time pickers end **/

        if (this.item == null) {
            // create new Item
            this.item = new Item();

        } else {

            // update item
            EditText mName = view.findViewById(R.id.edit_text_name);
            EditText mNotes = view.findViewById(R.id.text_edit_notes_edit);
            //EditText mPics = view.findViewById(R.id.edit_text_pic_edit);
            CheckBox isDefCheckBox = view.findViewById(R.id.is_default_checkbox);
            LinearLayout foodDataLayout = view.findViewById(R.id.editview_food_data_layout);
            LinearLayout reminderDataLayout = view.findViewById(R.id.editview_reminder_data_layout);
            LinearLayout weightDataLayout = view.findViewById(R.id.editview_weight_data_layout);

            typeSpinner.setSelection(AppUtils.getSelectionIndex(typeOptionMap.keySet(), item.getType()));

            if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype1)) ||
                    this.item.getType().equals(getResources().getString(R.string.enum_itemtype2)) ||
                    this.item.getType().equals(getResources().getString(R.string.enum_itemtype3)) ||
                    this.item.getType().equals(getResources().getString(R.string.enum_itemtype8))) {
                weightDataLayout.setVisibility(View.VISIBLE);
                EditText mWeight = view.findViewById(R.id.edit_text_weight);
                if (item.getWeight() != null)
                    mWeight.setText(String.valueOf(item.getWeight()));

            } else {
                //((ViewGroup)weightDataLayout.getParent()).removeView(weightDataLayout);
                weightDataLayout.setVisibility(View.INVISIBLE);
            }

            if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype3))) {
                foodDataLayout.setVisibility(View.VISIBLE);
                EditText mEnergy = view.findViewById(R.id.edit_text_energy);
                EditText mProtein = view.findViewById(R.id.edit_text_protein);
                if (item.getEnergy() != null)
                    mEnergy.setText(String.valueOf(item.getEnergy()));
                if (item.getProtein() != null)
                    mProtein.setText(String.valueOf(item.getProtein()));
            } else {
                //((ViewGroup)foodDataLayout.getParent()).removeView(foodDataLayout);
                foodDataLayout.setVisibility(View.INVISIBLE);
            }

            mName.setText(item.getName());
            mNotes.setText(item.getNotes());
            //mPics.setText(item.getPic());

            if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype5)) ||
                    this.item.getType().equals(getResources().getString(R.string.enum_itemtype7))) {
                reminderDataLayout.setVisibility(View.VISIBLE);
                mDeadline.setText(item.getDeadline());
            } else {
                //((ViewGroup)reminderDataLayout.getParent()).removeView(reminderDataLayout);
                reminderDataLayout.setVisibility(View.INVISIBLE);
            }

            isDefCheckBox.setChecked(item.isDefault());
        }

    }

    @Override
    protected int getLayout() {
        return R.layout.editview_item_content_layout;
    }

}
