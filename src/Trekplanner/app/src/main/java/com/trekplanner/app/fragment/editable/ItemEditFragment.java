package com.trekplanner.app.fragment.editable;

import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Item;

/**
 * Created by Sami
 *
 * Edit fragment for Items
 */
public class ItemEditFragment extends EditFragment {

    private Item item;

    private Spinner mTypeSpinner;
    private Spinner mStatusSpinner;
    private Spinner mIsDefaultSpinner;

    private EditText mWeight;
    private EditText mName;
    private EditText mNotes;
    private EditText mPics;
    private EditText mEnergy;
    private EditText mProtein;
    private EditText mDeadline;

    private String mType;

    private  String mStatus;

    private boolean mDefault;

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

        // TODO: update all fields to this.item

        String typeString = mType;
        String statusString = mStatus;
        Boolean isDefaulbool = mDefault;

        String weightString = mWeight.getText().toString().trim();
        Double weight = Double.parseDouble(weightString);
        String name = mName.getText().toString().trim();
        String notes = mNotes.getText().toString().trim();
        String pics = mPics.getText().toString().trim();
        String energyString = mEnergy.getText().toString().trim();
        Double energy = Double.parseDouble(energyString);
        String proteinString = mProtein.getText().toString().trim();
        Double protein = Double.parseDouble(proteinString);
        String deadlineString = mDeadline.getText().toString().trim();

        this.item.setType(typeString);
        this.item.setStatus(statusString);
        this.item.setDefault(isDefaulbool);
        this.item.setWeight(weight);
        this.item.setName(name);
        this.item.setNotes(notes);
        this.item.setPic(pics);
        this.item.setEnergy(energy);
        this.item.setProtein(protein);
        this.item.setDeadline(deadlineString);

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
        TextView headerText
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_text);

        headerImageView.setImageResource(R.drawable.item);
        headerText.setText(R.string.term_item);

        mTypeSpinner = (Spinner) view.findViewById(R.id.spinner_type);
        mType = item.getType();

        mStatusSpinner = (Spinner) view.findViewById(R.id.spinner_status);
        mStatus = item.getStatus();

        mIsDefaultSpinner = (Spinner) view.findViewById(R.id.spinner_def);
        mDefault = item.isDefault();

        mWeight = (EditText) view.findViewById(R.id.edit_text_weight);
        mName = (EditText) view.findViewById(R.id.edit_text_name);
        mNotes = (EditText) view.findViewById(R.id.text_edit_notes_edit);
        mPics = (EditText) view.findViewById(R.id.edit_text_pic_edit);
        mEnergy = (EditText) view.findViewById(R.id.edit_text_energy);
        mProtein = (EditText) view.findViewById(R.id.edit_text_protein);
        mDeadline = (EditText) view.findViewById(R.id.edit_text_deadline);

        mWeight.setText(item.getWeight().toString().trim());
        mName.setText(item.getName());
        mNotes.setText(item.getNotes());
        mPics.setText(item.getPic());
        mEnergy.setText(item.getEnergy().toString().trim());
        mProtein.setText(item.getProtein().toString().trim());
        mDeadline.setText(item.getDeadline());

        spinnerType();
        spinnerStatus();
        spinnerDefault();

    }

    @Override
    protected int getLayout() {
        return R.layout.editview_item_content_layout;
    }

    private void spinnerType(){
        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_type_options, android.R.layout.simple_dropdown_item_1line);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        if(mTypeSpinner == null){
            return;
        }else{
            mTypeSpinner.setAdapter(typeSpinnerAdapter);
        }

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(getString(R.string.item_type_1))){
                        mType = getString(R.string.item_type_1);
                    }else if(selection.equals(getString(R.string.item_type_2))){
                        mType = getString(R.string.item_type_2);
                    }else if(selection.equals(getString(R.string.item_type_3))){
                        mType = getString(R.string.item_type_3);
                    }else if(selection.equals(getString(R.string.item_type_4))){
                        mType = getString(R.string.item_type_4);
                    }else if(selection.equals(getString(R.string.item_type_5))){
                        mType = getString(R.string.item_type_5);
                    }else if(selection.equals(getString(R.string.item_type_6))){
                        mType = getString(R.string.item_type_6);
                    }else if(selection.equals(getString(R.string.item_type_7))){
                        mType = getString(R.string.item_type_7);
                    }else{
                        mType = getString(R.string.item_type_8);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mType = item.getType();
            }
        });


    }

    private void spinnerStatus(){

        ArrayAdapter typeStatusAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_status_options, android.R.layout.simple_dropdown_item_1line);

        typeStatusAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mStatusSpinner.setAdapter(typeStatusAdapter);

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(getString(R.string.item_status_1))){
                        mStatus = getString(R.string.item_status_1);
                    }else if(selection.equals(getString(R.string.item_status_2))){
                        mStatus = getString(R.string.item_status_2);
                    }else{
                        mStatus = getString(R.string.item_status_3);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = item.getStatus();
            }
        });
    }

    private void spinnerDefault(){

        ArrayAdapter isDefaultAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_default_options, android.R.layout.simple_dropdown_item_1line);

        isDefaultAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mIsDefaultSpinner.setAdapter(isDefaultAdapter);

        mIsDefaultSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selection = (String) adapterView.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selection)){
                    if(selection.equals(1)){
                        mDefault = true;
                    }else{
                        mDefault = false;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mDefault = item.isDefault();
            }
        });
    }
}
