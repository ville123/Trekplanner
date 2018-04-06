package com.trekplanner.app.fragment.listable;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.activity.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.ItemAdapter;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for Item list
 */
public class ItemListFragment extends ListFragment implements ListFragment.ListViewActionListener {

    private static ItemListFragment instance;
    private ItemAdapter adapter;

    private Integer sortOrder = AppUtils.SORT_ORDER_BY_NAME;

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
        headerImageView.setImageResource(R.drawable.items);

        // empty header background
        View headerLayout
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.main_header_layout);
        headerLayout.setBackgroundResource(0);

        //Actionbar content
        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_items));

        // setup sort -actions
        final TextView action1View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action1);
        action1View.setText(R.string.phrase_sort_by_itemtype);

        final TextView action2View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action2);
        action2View.setText(R.string.phrase_sort_by_name);

        // sort order
        if (this.sortOrder == AppUtils.SORT_ORDER_BY_NAME) {
            action2View.setTypeface(null, Typeface.BOLD);
            action1View.setTypeface(null, Typeface.NORMAL);
        } else {
            action1View.setTypeface(null, Typeface.BOLD);
            action2View.setTypeface(null, Typeface.NORMAL);
        }

        action1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setListRows(db.getItems(null, AppUtils.SORT_ORDER_BY_TYPE));
                adapter.notifyDataSetChanged();
                sortOrder = AppUtils.SORT_ORDER_BY_TYPE;
                action1View.setTypeface(null, Typeface.BOLD);
                action2View.setTypeface(null, Typeface.NORMAL);
            }
        });

        action2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.setListRows(db.getItems(null, AppUtils.SORT_ORDER_BY_NAME));
                adapter.notifyDataSetChanged();
                sortOrder = AppUtils.SORT_ORDER_BY_NAME;
                action2View.setTypeface(null, Typeface.BOLD);
                action1View.setTypeface(null, Typeface.NORMAL);
            }
        });
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_ItemListFrag", "Preparing ItemListView data with item rowid " + this.rowId);

        this.adapter = new ItemAdapter(this.getActivity(), this);

        // itemlist contains items from db
        if (this.sortOrder == AppUtils.SORT_ORDER_BY_NAME) {
            adapter.setListRows(db.getItems(null, AppUtils.SORT_ORDER_BY_NAME));
        } else {
            adapter.setListRows(db.getItems(null, AppUtils.SORT_ORDER_BY_TYPE));
        }
        listView.setAdapter(adapter);
    }

    // floating button clicked, this case its to add new Item
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.ITEM_LIST_ACTION_ID, view);
    }

    // list view item forward button clicked
    @Override
    public void onForwardButtonClick(Object o) {
        // all UI navigation is handled by MainActivity
        ((MainActivity) this.getActivity()).onForwardButtonClick((Item) o);
    }

    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        // no such funtion in item list
    }

    @Override
    public void onDeleteButtonClicked(Object o) {
        // TODO: implemented delete for item from list
    }

    // save action fired from itemlistview
    @Override
    public void saveButtonClicked(Object o) {
        db.saveItem((Item) o);
        AppUtils.showOkMessage(getView(), R.string.phrase_save_success);

    }
}
