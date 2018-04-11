package com.trekplanner.app.fragment.listable;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.activity.MainActivity;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.ItemAdapter;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

import java.util.HashMap;
import java.util.Map;

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
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_icon);
        headerImageView.setImageResource(R.drawable.items);

        // empty header image
        ImageView hdrImage = getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_picture);
        hdrImage.setImageBitmap(null);

        // hide camerabutton
        ImageButton camBtn = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_camera_button);
        camBtn.setVisibility(View.INVISIBLE);

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
    public void onClick(final View view) {

        AppUtils.showItemTypeSelectionPopup(
                view,
                this.getActivity(),
                R.menu.item_type_selection_menu,
                new PopupMenu.OnMenuItemClickListener() {

                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Map<String, Object> attribs = new HashMap<>();
                        String type;
                        switch (menuItem.getItemId()) {
                            case R.id.item_type_2:
                                type = getResources().getString(R.string.enum_itemtype2);
                                break;
                            case R.id.item_type_3:
                                type = getResources().getString(R.string.enum_itemtype3);
                                break;
                            case R.id.item_type_4:
                                type = getResources().getString(R.string.enum_itemtype4);
                                break;
                            case R.id.item_type_5:
                                type = getResources().getString(R.string.enum_itemtype5);
                                break;
                            case R.id.item_type_6:
                                type = getResources().getString(R.string.enum_itemtype6);
                                break;
                            case R.id.item_type_7:
                                type = getResources().getString(R.string.enum_itemtype7);
                                break;
                            case R.id.item_type_8:
                                type = getResources().getString(R.string.enum_itemtype8);
                                break;
                            default:
                                type = getResources().getString(R.string.enum_itemtype1);
                        }
                        attribs.put(AppUtils.ITEM_TYPE_KEY, type);
                        ((MainActivity) getActivity()).onListViewFloatingButtonClick(AppUtils.ITEM_LIST_ACTION_ID, view, attribs);
                        return true;
                    }
                });
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

    public void refreshItemList(String query){
        adapter.setListRows(db.getItemListByKeyword(query));
        adapter.notifyDataSetChanged();
    }
}
