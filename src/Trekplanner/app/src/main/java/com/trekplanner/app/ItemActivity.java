package com.trekplanner.app;

import android.app.LoaderManager;
import android.content.ContentUris;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.trekplanner.app.db.ItemContract;
import com.trekplanner.app.db.ItemCursorAdapter;
import com.trekplanner.app.model.Item;

import java.util.ArrayList;

public class ItemActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final int ITEM_LOADER = 0;

    ItemCursorAdapter mCursorAdapter;

    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        //Creating array of Items
        item = new Item();
        item.setType("Kantovaruste");
        item.setStatus("OK");
        item.setWeight(0.76);
        item.setName("Camera");
        item.setNotes("Canon E0S 70D");
        item.setPic("No picture");
        item.setDefault(true);
        item.setEnergy(0.0);
        item.setProtein(0.0);
        item.setDeadline("23.3.2018");

        // Find the ListView which will be populated
        ListView itemListView = (ListView) findViewById(R.id.list_of_items);

        //Setup an Adapter to create view on the listView
        mCursorAdapter = new ItemCursorAdapter(this, null);
        itemListView.setAdapter(mCursorAdapter);

        Button insertButton = findViewById(R.id.insert_button);
        Button delete_allButton = findViewById(R.id.display_db_info);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertItem();
            }
        });

        delete_allButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ItemActivity.this, ItemEditorActivity.class);;
                //startActivity(intent);
                deleteAllItems();
            }
        });


        //Set item click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ItemActivity.this, ItemEditorActivity.class);

                Uri currentItemUri = ContentUris.withAppendedId(
                        ItemContract.ItemEntry.CONTENT_URI, id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });

        //Kick the loader
        getLoaderManager().initLoader(ITEM_LOADER, null, this);
    }

    //Inserting item to the database
    private void insertItem(){
        //Gets the databse in write mode
        //SQLiteDatabase db = itemDbHelper.getWritableDatabase();

        //Create content values
        ContentValues values = new ContentValues();
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_TYPE, item.getType());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_STATUS, item.getStatus());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_NAME, item.getName());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_NOTES, item.getNotes());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_PIC, item.getPic());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT, item.isDefault());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE, item.getDeadline());

        // Insert a new item into the provider, returning the content URI for the new item.
        Uri newUri = getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newUri == null) {
            // If the new content URI is null, then there was an error with insertion.
            Toast.makeText(this, "Error with saving the item",
                    Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast.
            Toast.makeText(this, "Insertion successful",
                    Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        //Define a projecton that specifies items column
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

        //This loader will excute the contentProvider' s querry method on a background method
        return new CursorLoader(this,      //Parent activity context
                ItemContract.ItemEntry.CONTENT_URI, //Provider content URI to query
                projection,                        //Columns to include in the resulting Cursor
                null,                     //No selection clause
                null,                  //No selection argument
                null);                    //Default sort order
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        //Update
        mCursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // Callback called when the data needs to be deleted
        mCursorAdapter.swapCursor(null);
    }

    /**
     * Helper method to delete all items in the database.
     */
    private void deleteAllItems() {
        int rowsDeleted = getContentResolver().delete(ItemContract.ItemEntry.CONTENT_URI,
                null, null);
        Log.v("ItemActivity", rowsDeleted + " rows deleted from the database");
    }
}
