package com.trekplanner.app.db;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.trekplanner.app.R;

/**
 * Created by Shakur on 23.3.2018.
 */

public class ItemCursorAdapter extends CursorAdapter {

    public ItemCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find individual views that we want to modify in the list item layout
        TextView type_text_view = (TextView) view.findViewById(R.id.type);
        TextView status_text_view = (TextView) view.findViewById(R.id.status);
        TextView weight_text_view = (TextView) view.findViewById(R.id.weight);
        TextView name_text_view = (TextView) view.findViewById(R.id.name);
        TextView notes_text_view = (TextView) view.findViewById(R.id.notes);
        TextView pic_text_view = (TextView) view.findViewById(R.id.pic);
        TextView default_text_view = (TextView) view.findViewById(R.id.default_item);
        TextView energy_text_view = (TextView) view.findViewById(R.id.energy);
        TextView protein_text_view = (TextView) view.findViewById(R.id.protein);
        TextView date_text_view = (TextView) view.findViewById(R.id.date);

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
        type_text_view.setText(currentType);
        status_text_view.setText(currentStatus);
        weight_text_view.setText(currentWeight+" kg");
        name_text_view.setText(currentName);
        notes_text_view.setText(currentNotes);
        pic_text_view.setText(currentPic);
        default_text_view.setText(currentDefault+"");
        energy_text_view.setText(currentEnergy+"");
        protein_text_view.setText(currentProtein+"");
        date_text_view.setText(currentDate);
    }
}
