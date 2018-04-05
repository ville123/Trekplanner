package com.trekplanner.app.fragment.editable;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Item;

import java.util.Locale;

/**
 * Created by Sami
 *
 * Edit fragment for Items
 */
public class ItemEditFragment extends EditFragment {

    private Item item;

    private Spinner mTypeSpinner;
    private Spinner mStatusSpinner;

    private EditText mWeight;
    private EditText mName;
    private EditText mNotes;
    private EditText mPics;
    private EditText mEnergy;
    private EditText mProtein;
    private EditText mDeadline;

    private CheckBox isDefCheckBox;

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
        boolean isDefaulbool = mDefault;

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

        mTypeSpinner = (Spinner) view.findViewById(R.id.spinner_type);
        mType = item.getType();

        mStatusSpinner = (Spinner) view.findViewById(R.id.spinner_status);
        mStatus = item.getStatus();

        mWeight = (EditText) view.findViewById(R.id.edit_text_weight);
        mName = (EditText) view.findViewById(R.id.edit_text_name);
        mNotes = (EditText) view.findViewById(R.id.text_edit_notes_edit);
        mPics = (EditText) view.findViewById(R.id.edit_text_pic_edit);
        mEnergy = (EditText) view.findViewById(R.id.edit_text_energy);
        mProtein = (EditText) view.findViewById(R.id.edit_text_protein);
        mDeadline = (EditText) view.findViewById(R.id.edit_text_deadline);

        isDefCheckBox = (CheckBox) view.findViewById(R.id.is_default_checkbox);

        mDefault = item.isDefault();

        if(mDefault){
            isDefCheckBox.setChecked(mDefault);
        }else{
            isDefCheckBox.setChecked(mDefault);
        }

        if(TextUtils.isEmpty(mWeight.getText().toString()) && mWeight == null){
            /**
             * This prints 'null' instead of 0.00
             * TODO: Need to find out why?
             */
            mWeight.setText(String.format(Locale.UK, "%.4f", 0.0));
        }else{
            mWeight.setText(String.format(Locale.UK,"%.4f", item.getWeight()));
        }

        if(TextUtils.isEmpty(mName.getText().toString()) && mName == null){
            mName.setText("");
        }else{
            mName.setText(item.getName());
        }

        if(TextUtils.isEmpty(mNotes.getText().toString()) && mNotes == null){
            mNotes.setText("");
        }else{
            mNotes.setText(item.getNotes());
        }

        if(TextUtils.isEmpty(mPics.getText().toString()) && mPics == null){
            mPics.setText("");
        }else{
            mPics.setText(item.getPic());
        }

        if(TextUtils.isEmpty(mEnergy.getText().toString()) && mEnergy == null ){
            /**
             * This prints Energy is 'null' instead of 0.0
             * TODO: Need to find out why?
             */
            mEnergy.setText(String.format(Locale.UK, "%.4f", 0.0));
        }else{
            mEnergy.setText(String.format(Locale.UK,"%.4f", item.getEnergy()));
        }

        if(TextUtils.isEmpty(mProtein.getText().toString()) && mProtein == null ){
            /**
             * This prints 'null' instead of 0.0
             * TODO: Need to find out why?
             */
            mProtein.setText(String.format(Locale.UK,"%.4f", 0.0));
        }else{
            mProtein.setText(String.format(Locale.UK,"%.4f", item.getProtein()));
        }

        if(TextUtils.isEmpty(mDeadline.getText().toString()) && mDeadline == null){
            mDeadline.setText("");
        }else{
            mDeadline.setText(item.getDeadline());
        }

        spinnerType();
        spinnerStatus();

    }

    @Override
    protected int getLayout() {
        return R.layout.editview_item_content_layout;
    }

    /**
     * Spinner is not getting item type from the default loaded items, After editing spinner
     * changes normally
     * TODO: Couldn't find a solution
     * Spinner for choosing item type
     */
    private void spinnerType(){
        final ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.array_type_options, android.R.layout.simple_dropdown_item_1line);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mTypeSpinner.setAdapter(typeSpinnerAdapter);

        if(mType != null){
            int spinnerPosition = typeSpinnerAdapter.getPosition(mType);
            mTypeSpinner.setSelection(spinnerPosition, true);
        }

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mType = mTypeSpinner.getSelectedItem().toString();
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

        if(mStatus != null){
            mStatusSpinner.post(new Runnable() {
                public void run() {
                    mStatusSpinner.setSelection(getIndex(mStatusSpinner, mStatus));
                }
            });
        }

        mStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mStatus = mStatusSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mStatus = item.getStatus();
            }
        });
    }

    /**
     *Getting spinner position
     * @param spinner
     * @param myString
     * @return
     */
    private int getIndex(Spinner spinner, String myString){
        for (int i = 0; i < spinner.getCount(); i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }
}
