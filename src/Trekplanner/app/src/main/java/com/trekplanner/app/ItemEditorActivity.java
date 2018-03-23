package com.trekplanner.app;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.trekplanner.app.db.ItemContract;

public class ItemEditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int EXISTING_ITEM_LOADER = 0;

    private Uri mCurrentItemUri;

    private EditText mTypeEditText;
    private EditText mStatusEditText;
    private EditText mWeightEditText;
    private EditText mNameEditText;
    private EditText mNotesEditText;
    private EditText mPicEditText;
    private EditText mDefaultEditText;
    private EditText mEnergyEditText;
    private EditText mProteinEditText;
    private EditText mDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_editor);

        Intent intent = getIntent();

        mCurrentItemUri = intent.getData();

        mTypeEditText = findViewById(R.id.edit_text_type);
        mStatusEditText = findViewById(R.id.edit_text_status);
        mWeightEditText = findViewById(R.id.edit_text_weight);
        mNameEditText = findViewById(R.id.edit_text_name);
        mNotesEditText = findViewById(R.id.edit_text_notes);
        mPicEditText = findViewById(R.id.edit_text_pic);
        mDefaultEditText = findViewById(R.id.edit_text_default);
        mEnergyEditText = findViewById(R.id.edit_text_energy);
        mProteinEditText = findViewById(R.id.edit_text_protein);
        mDateEditText = findViewById(R.id.edit_text_date);

        Button button_update = findViewById(R.id.update);
        Button button_delete = findViewById(R.id.delete);

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem();
                finish();
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem();
            }
        });

        setTitle("Update item");

        // Initialize a loader to read the item data from the database
        // and display the current values in the editor
        getLoaderManager().initLoader(EXISTING_ITEM_LOADER, null, this);
    }

    /**
     * Get user input from editor and save new pet into database.
     */
    private void updateItem() {
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String typeString = mTypeEditText.getText().toString().trim();
        String statusString = mStatusEditText.getText().toString().trim();
        String weightString = mWeightEditText.getText().toString().trim();
        Double weight = Double.parseDouble(weightString);
        String nameString = mNameEditText.getText().toString().trim();
        String notesString = mNotesEditText.getText().toString().trim();
        String picString = mPicEditText.getText().toString().trim();
        String defString = mDefaultEditText.getText().toString().trim();
        String energyString = mEnergyEditText.getText().toString().trim();
        Double energy = Double.parseDouble(energyString);
        String proteinString = mProteinEditText.getText().toString().trim();
        Double protein = Double.parseDouble(proteinString);
        String dateString = mDateEditText.getText().toString().trim();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_TYPE, typeString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_STATUS, statusString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT, weight);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, nameString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_NOTES, notesString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_PIC, picString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT, defString);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_ENERGY, energy);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN, protein);
        contentValues.put(ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE, dateString);

        // Update the item with content URI: mCurrentPetUri
        // because mCurrentPetUri will already identify the correct row in the database that
        // we want to modify.
        int rowsAffected = getContentResolver().update(mCurrentItemUri,
                contentValues, null, null);

        // Show a toast message depending on whether or not the update was successful.
        if (rowsAffected == 0) {
            // If no rows were affected, then there was an error with the update.
            Toast.makeText(this, "Error with the update",
                    Toast.LENGTH_SHORT).show();
            Log.v("EditorActivity", "rowsAffected == 0");
        } else {
            // Otherwise, the update was successful and we can display a toast.
            Toast.makeText(this, "update successful",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Perform the deletion of the pet in the database.
     */
    private void deleteItem() {

        // Call the ContentResolver to delete the item at the given content URI.
        // Pass in null for the selection and selection args because the mCurrentPetUri
        // content URI already identifies the pet that we want.
        int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);

        // Show a toast message depending on whether or not the delete was successful.
        if (rowsDeleted == 0) {
            // If no rows were deleted, then there was an error with the delete.
            Toast.makeText(this, "deletion failed",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the delete was successful and we can display a toast.
            Toast.makeText(this, "deletion successful",
                    Toast.LENGTH_SHORT).show();
        }


        // Close the activity
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String [] projection = {
                ItemContract.ItemEntry._ID,
                ItemContract.ItemEntry.COLUMN_ITEM_TYPE,
                ItemContract.ItemEntry.COLUMN_ITEM_STATUS,
                ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT,
                ItemContract.ItemEntry.COLUMN_ITEM_NAME,
                ItemContract.ItemEntry.COLUMN_ITEM_NOTES,
                ItemContract.ItemEntry.COLUMN_ITEM_PIC,
                ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT,
                ItemContract.ItemEntry.COLUMN_ITEM_ENERGY,
                ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN,
                ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE

        };

        return new CursorLoader(this,
                mCurrentItemUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Bail early if the cursor is null or there is less than 1 row in the cursor
        if(cursor == null || cursor.getCount() < 1){
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if(cursor.moveToFirst()){

            //Find the columns of the item attributes
            int idColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry._ID);
            int typeColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_TYPE);
            int statusColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_STATUS);
            int weightColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT);
            int nameColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NAME);
            int notesColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_NOTES);
            int picColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PIC );
            int defaultColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT);
            int energyColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_ENERGY);
            int proteinColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN);
            int deadlineColumnIndex = cursor.getColumnIndex(ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE);

            //Read the item attributes
            int currentId = cursor.getInt(idColumnIndex);
            String currentType = cursor.getString(typeColumnIndex);
            String currentStatus = cursor.getString(statusColumnIndex);
            Double currentWeight = cursor.getDouble(weightColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentNotes = cursor.getString(notesColumnIndex);
            String currentPic = cursor.getString(picColumnIndex);
            int currentDefault = cursor.getInt(defaultColumnIndex);
            Double currentEnergy = cursor.getDouble(energyColumnIndex);
            Double currentProtein = cursor.getDouble(proteinColumnIndex);
            String currentDate = cursor.getString(deadlineColumnIndex);

            // Update the TextViews with the attributes for the current item
            mTypeEditText.setText(currentType);
            mStatusEditText.setText(currentStatus);
            mWeightEditText.setText(currentWeight+"");
            mNameEditText.setText(currentName);
            mNotesEditText.setText(currentNotes);
            mPicEditText.setText(currentPic);
            mDefaultEditText.setText(currentDefault+"");
            mEnergyEditText.setText(currentEnergy+"");
            mProteinEditText.setText(currentProtein+"");
            mDateEditText.setText(currentDate);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mTypeEditText.setText("");
        mStatusEditText.setText("");
        mWeightEditText.setText("");
        mNameEditText.setText("");
        mNotesEditText.setText("");
        mPicEditText.setText("");
        mDefaultEditText.setText("");
        mEnergyEditText.setText("");
        mProteinEditText.setText("");
        mDateEditText.setText("");
    }
}
