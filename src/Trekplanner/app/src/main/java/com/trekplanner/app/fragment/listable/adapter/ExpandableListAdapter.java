package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.BaseExpandableListAdapter;

import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.model.Item;

/**
 * Created by Sami
 *
 * Base class for all list adapters.
 * Adds the expandable list header action listener.
 */
public abstract class ExpandableListAdapter extends BaseExpandableListAdapter {

    protected ListFragment.ListViewActionListener viewActionListener;
    protected Context context;

    public ExpandableListAdapter(Context context, ListFragment.ListViewActionListener listener) {
        Log.d("TREK_ListviewAdapter", "Adding action as action listener");
        this.context = context;
        // adding listener for all adapter level actions
        this.viewActionListener = listener;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}