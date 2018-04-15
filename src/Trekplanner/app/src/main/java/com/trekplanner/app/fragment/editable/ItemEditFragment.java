package com.trekplanner.app.fragment.editable;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Sami
 *
 * Edit fragment for Items
 */
public class ItemEditFragment extends EditFragment {

    private Item item;
    private static Map<String, String> typeOptionMap;

    // field for trek-private items
    private String trekId;

    public static ItemEditFragment getNewInstance(DbHelper db, Item item, String trekId, String itemType) {
        Log.d("TREK_ItemEditFragment", "New TrekEditFragment -instance created");

        // cant use singelton -pattern for item edit page since context (item) is changing
        ItemEditFragment instance = new ItemEditFragment();
        instance.db = db;
        instance.item = item;
        instance.trekId = trekId;
        if (instance.item == null) {
            instance.item = new Item();
            instance.item.setType(itemType);
        }
        return instance;
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_ItemEditFragment", "Building TrekEdit view");

        // setting page header content
        ImageView headerImageView
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_icon);
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

        //populate type and status key-values, if not done already
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
        ImageView btnDatePicker = view.findViewById(R.id.editview_select_date_button);
        ImageView btnTimePicker = view.findViewById(R.id.editview_select_time_button);
        final EditText mDeadline = view.findViewById(R.id.edit_text_deadline);

        if (btnDatePicker != null) {
            btnDatePicker.setOnClickListener(AppUtils.getDatePickerListener(this.getActivity(), mDeadline));
            btnTimePicker.setOnClickListener(AppUtils.getTimePickerListener(this.getActivity(), mDeadline));
        }
        /** date time pickers end **/

        /** checkbox **/
        CheckBox isDefCheckBox = view.findViewById(R.id.is_default_checkbox);

        if (isDefCheckBox != null) {
            isDefCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView,
                                             boolean isChecked) {
                    item.setDefault(isChecked);
                }
            });
        }
        /** checkbox end **/

        /** editor for notes **/
        final TextView notesFld = view.findViewById(R.id.text_edit_notes_edit);
        AppUtils.buildEditorForNotes(this.getActivity(), notesFld);
        /** editor for notes end **/

        if (this.item.getId() != null && !this.item.getId().isEmpty()) {

            // update item
            EditText mName = view.findViewById(R.id.edit_text_name);
            TextView mNotes = view.findViewById(R.id.text_edit_notes_edit);

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
                ImageView hdrImage = getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_picture);
                hdrImage.setImageBitmap(AppUtils.decodeToBitmap(item.getPic()));
            }
        }

    }

    @Override
    public void onClick(View view) {
        Log.d("TREK_ItemEditFragment", "Save button clicked");

        CoordinatorLayout parentView = (CoordinatorLayout) view.getParent();

        // first close the keyboard
        AppUtils.closeInputwidget(getActivity(), parentView);

        EditText mWeight = parentView.findViewById(R.id.edit_text_weight);
        EditText mName = parentView.findViewById(R.id.edit_text_name);
        TextView mNotes = parentView.findViewById(R.id.text_edit_notes_edit);
        EditText mEnergy = parentView.findViewById(R.id.edit_text_energy);
        EditText mProtein = parentView.findViewById(R.id.edit_text_protein);
        EditText mDeadline = parentView.findViewById(R.id.edit_text_deadline);

        if (mWeight!= null && !mWeight.getText().toString().isEmpty())
            this.item.setWeight(Double.valueOf(mWeight.getText().toString()));

        this.item.setName(mName.getText().toString());
        this.item.setNotes(mNotes.getText().toString());

        if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype3))) {
            if (mEnergy!= null && !mEnergy.getText().toString().isEmpty())
                this.item.setEnergy(Double.valueOf(mEnergy.getText().toString()));

            if (mProtein!=null && !mProtein.getText().toString().isEmpty())
                this.item.setProtein(Double.valueOf(mProtein.getText().toString()));
        }

        if (this.item.getType().equals(getResources().getString(R.string.enum_itemtype5)) ||
                this.item.getType().equals(getResources().getString(R.string.enum_itemtype7))) {
            if (mDeadline!=null && !mDeadline.getText().toString().isEmpty()) {
                this.item.setDeadline(mDeadline.getText().toString());
            }
        }

        if(TextUtils.isEmpty(mName.getText())) {
            mName.setError(getResources().getString(R.string.phrase_name_required));
        } else {

            if (this.trekId != null && !this.trekId.isEmpty()) {
                // save trek specific item to second item table
                db.saveTrekSpecificItem(this.item, this.trekId);
            } else {
                // save normal item to items
                db.saveItem(this.item);
            }

            AppUtils.showOkMessage(view, R.string.phrase_save_success);
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
            ImageView hdrImage = getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_picture);
            hdrImage.setImageBitmap(AppUtils.decodeToBitmap(item.getPic()));

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
