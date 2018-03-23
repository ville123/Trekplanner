package com.trekplanner.app.fragment.listable;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;

public abstract class ListFragment extends Fragment implements View.OnClickListener {

    ExpandableListView listView;
    Long rowId;
    DbHelper db;

    public interface ListViewActionListener {
        void onForwardButtonClick(Object o);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(getLayout(), container, false);

        FloatingActionButton fab = view.findViewById(R.id.listview_floating_action_btn);
        fab.setOnClickListener(getActionButtonOnClickListener());

        listView = view.findViewById(R.id.expandable_listview);

        listView.setGroupIndicator(null);

        prepareListViewData();

        // sulkee muut rivit kun avaa uuden
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    listView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });

        buildView(view);

        return view;
    }

    protected abstract int getLayout();

    protected abstract void buildView(View view);

    protected abstract void prepareListViewData();

    protected View.OnClickListener getActionButtonOnClickListener() {
        return this;
    }

}
