package com.trekplanner.app.fragment.listable.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.BaseExpandableListAdapter;

import com.trekplanner.app.fragment.listable.ListFragment;

public abstract class ListviewAdapter extends BaseExpandableListAdapter {

    protected ListFragment.ListViewActionListener actionListener;
    protected Context context;

    public ListviewAdapter(Context context) {
        Log.d("TREK_ListviewAdapter", "Adding action as action listener");
        this.context = context;
        if (context instanceof ListFragment.ListViewActionListener) {
            this.actionListener = (ListFragment.ListViewActionListener)context;
        } else {
            throw new IllegalArgumentException("ListViewAdapter can not set action listener");
        }
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