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
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.TrekItem;

/**
 * Created by Sami
 *
 * Base class for all list fragments
 */
public abstract class ListFragment extends Fragment implements View.OnClickListener {

    ExpandableListView listView;
    Long rowId; // ties this instance to a certain item or trek
    DbHelper db;

    // interface for handling actions from adapter -level
    public interface ListViewActionListener {
        void onForwardButtonClick(Object o);
        void onModifyCountButtonClicked(TrekItem trekItem);
        void onDeleteButtonClicked(Long rowId);
        void saveButtonClicked(Item item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        // getlayout is called for subclass, thus returning specific layout for subclass
        View view = inflater.inflate(getLayout(), container, false);

        FloatingActionButton fab = view.findViewById(R.id.listview_floating_action_btn);
        fab.setOnClickListener(getActionButtonOnClickListener());

        listView = view.findViewById(R.id.expandable_listview);

        // hides the >-mark from the header
        listView.setGroupIndicator(null);

        // prepareListViewData is called for subclass, thus adding subclass specific data to list
        prepareListViewData();

        // closes all other rows than the clicked one
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition != previousItem )
                    listView.collapseGroup(previousItem );
                previousItem = groupPosition;
            }
        });

        // buildView is called for subclass, thus building subclass specific view
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
