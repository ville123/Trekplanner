package com.trekplanner.app.fragment.editable;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.model.Item;

public class ItemEditFragment extends EditFragment {

    private Item item;

    public static ItemEditFragment getNewInstance(DbHelper db, Item item) {
        Log.d("TREK_ItemEditFragment", "New TrekEditFragment -instance created");
        ItemEditFragment instance = new ItemEditFragment();
        instance.db = db;
        instance.item = item;
        return instance;
    }

    @Override
    public void onClick(View view) {
        Log.d("TREK_ItemEditFragment", "Save button clicked");
        // TODO: save or update item
        Snackbar.make(view, "varusteen " + this.item.getName() + " tilalla " + item.getStatus() + " " + R.string.phrase_save_success, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_ItemEditFragment", "Building TrekEdit view");
        TextView headerText = view.findViewById(R.id.view_header_text);
        headerText.setText(R.string.term_item);

        ImageView imageView = view.findViewById(R.id.view_header_image);
        imageView.setImageResource(R.drawable.item);

    }

    @Override
    protected int getLayout() {
        return R.layout.editview_item_content_layout;
    }
}
