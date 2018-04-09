package com.trekplanner.app.fragment.listable;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekItemAdapter;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for TrekItem list
 */
public class TrekItemListFragment extends ListFragment implements ListFragment.ListViewActionListener {

    private TrekItemAdapter adapter;

    public static TrekItemListFragment getNewInstance(DbHelper db, String trekId) {
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

    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);
        this.adapter = new TrekItemAdapter(this.getActivity(), this);

        // treklist contains items for a trek from db (rowId = Trek.Id)
        this.adapter.setListRows(db.getTrekItems(this.rowId));
        listView.setAdapter(adapter);
    }

    @Override
    public void updateDataSetWithQuery(String query) {
        // do nothing
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        // TODO: open item selection list
        Snackbar.make(view, "Tästä pitäisi avautua varusteiden valintalista retkelle", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onForwardButtonClick(Object o) {
        // no such button in trekItem -list
    }

    // add or substract count -button clicked on trekitem listview
    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        db.saveTrekItem(trekItem);
        //AppUtils.showOkMessage(getView(), R.string.phrase_save_success);
    }

    // delete -button clicked on trekitem listview
    @Override
    public void onDeleteButtonClicked(final Object o) {

        DialogInterface.OnClickListener yesListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                TrekItem trekItem = (TrekItem) o;
                db.deleteTrekItem(trekItem);
                adapter.removeFromListAndNotify(trekItem);
                //AppUtils.showOkMessage(getView(), R.string.phrase_delete_success);

            }
        };

        //noListener = null, so it only closes the dialog

        AppUtils.showConfirmDialog(getActivity(), R.string.phrase_confirm_delete, yesListener, null);

    }

    @Override
    public void saveButtonClicked(Object o) {
        db.saveTrekItem((TrekItem) o);
        AppUtils.showOkMessage(getView(), R.string.phrase_save_success);
    }
}
