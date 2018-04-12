package com.trekplanner.app.fragment.listable;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekItemAdapter;
import com.trekplanner.app.fragment.listable.adapter.TrekItemSelectionAdapter;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

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
        listView.setAdapter(this.adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {

        // open dialog for selecting multiple items for the trek

        List<Item> allItems = db.getItems(null, null); // TODO: some cache needed for all items!
        final List<Item> items = filterItems(allItems);
        CharSequence ids[] = new CharSequence[items.size()];
        int i=0;
        for (Item item : items) {
            ids[i++] = item.getName();
        }

        final TrekItemSelectionAdapter selectionAdapter =
                new TrekItemSelectionAdapter(items, this.getActivity());


        final AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setTitle(getString(R.string.phase_select_trekitems));
        builder.setAdapter(selectionAdapter, null);
        builder.setPositiveButton(getString(R.string.term_save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                adapter.notifyDataSetChanged();
            }
        });
        AlertDialog dialog = builder.create();
        ListView listView = dialog.getListView();
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item item = items.get(position);
                TrekItem titem = new TrekItem();
                titem.setItemId(item.getId());
                titem.setTrekId(rowId);
                titem.setItem(item);
                db.saveTrekItem(titem);
                adapter.add(titem);
                selectionAdapter.remove(item);
                selectionAdapter.notifyDataSetChanged();
            }
        });
        listView.setDivider(null);
        listView.setDividerHeight(2);
        dialog.show();
    }

    private List<Item> filterItems(List<Item> allItems) {
        List<Item> items = new ArrayList<>();
        List<TrekItem> trekItems = db.getTrekItems(this.rowId); // TODO: cache!

        // TODO: optimize!
        for (Item item : allItems) {
            boolean found = false;
            for (TrekItem titem : trekItems) {
                if (item.getId().equals(titem.getItemId())) {
                    found = true;
                    break;
                }
            }
            if (!found) items.add(item);
        }
        return items;
    }

    @Override
    public void onForwardButtonClick(Object o) {
        // no such button in trekItem -list
    }

    // add or substract count -button clicked on trekitem listview
    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        db.saveTrekItem(trekItem);
    }

    // delete -button clicked on trekitem listview
    @Override
    public void onDeleteButtonClicked(final Object o) {

        DialogInterface.OnClickListener yesListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                TrekItem trekItem = (TrekItem) o;
                db.deleteTrekItem(trekItem);
                adapter.remove(trekItem);
                adapter.notifyDataSetChanged();
            }
        };

        //noListener = null, so it only closes the dialog
        AppUtils.showConfirmDialog(getActivity(), R.string.phrase_confirm_delete, yesListener, null);

    }

    @Override
    public void saveButtonClicked(Object o) {
        db.saveTrekItem((TrekItem) o);
    }
}
