package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekItemAdapter;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for TrekItem list
 */
public class TrekItemListFragment extends ListFragment {

    public static TrekItemListFragment getNewInstance(DbHelper db, Long trekId) {
        Log.d("TREK_TrekItemListFrag", "Returning  new TrekItemListFragment -instance");

        // cant use singelton -pattern for trekitems since context (trek) is changing
        TrekItemListFragment instance = new TrekItemListFragment();
        instance.db = db;

        // id ties this instance to a certain trek
        instance.rowId = trekId;
        return instance;
    }

    @Override
    protected int getLayout() {
        return R.layout.editview_list_layout;
    }

    @Override
    protected void buildView(View view) {

        // nothing to build since trekitem -list has no page header (it is handled by MainEditFragment (tab-layout)
        Log.d("TREK_TrekItemListFrag", "Building ItemList view");
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);
        TrekItemAdapter adapter = new TrekItemAdapter(this.getActivity());

        // treklist contains items for a trek from db
        adapter.setListRows(db.getTrekItems(this.rowId));
        listView.setAdapter(adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.TREKITEM_LIST_ACTION_ID, view);
    }
}
