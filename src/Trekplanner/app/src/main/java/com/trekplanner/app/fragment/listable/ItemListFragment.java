package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.ItemAdapter;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for Item list
 */
public class ItemListFragment extends ListFragment {

    private static ItemListFragment instance;

    public static ItemListFragment getInstance(DbHelper db) {
        Log.d("TREK_ItemListFrag", "Returning ItemListFragment -instance");
        // using singelton -pattern for itemlist
        if (instance == null) {
            instance = new ItemListFragment();
            instance.db = db;
        }
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.listview_list_layout;
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_ItemListFrag", "Building ItemList view");

        // setting page header content
        ImageView headerImageView
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_image);
        TextView headerText
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_text);

        headerImageView.setImageResource(R.drawable.item);
        headerText.setText(R.string.term_items);
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_ItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);
        ItemAdapter adapter = new ItemAdapter(this.getActivity());

        // itemlist contains items from db
        adapter.setListRows(db.getItems(null));
        listView.setAdapter(adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.ITEM_LIST_ACTION_ID, view);
    }
}
