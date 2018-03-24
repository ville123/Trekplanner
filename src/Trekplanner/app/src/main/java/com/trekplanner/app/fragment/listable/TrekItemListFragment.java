package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekItemListAdapter;
import com.trekplanner.app.utils.AppUtils;

public class TrekItemListFragment extends ListFragment {

    public static TrekItemListFragment getNewInstance(DbHelper db, Long id) {
        Log.d("TREK_TrekItemListFrag", "Returning  new TrekItemListFragment -instance");
        TrekItemListFragment instance = new TrekItemListFragment();
        instance.db = db;
        instance.rowId = id;
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_list_layout;
    }

    @Override
    protected void buildView(View view) {
        Log.d("TREK_TrekItemListFrag", "Building ItemList view");
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);
        TrekItemListAdapter adapter = new TrekItemListAdapter(this.getActivity());
        adapter.setListRows(db.getItems(this.rowId));
        listView.setAdapter(adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.TREKITEM_LIST_ACTION_ID, view);
    }
}
