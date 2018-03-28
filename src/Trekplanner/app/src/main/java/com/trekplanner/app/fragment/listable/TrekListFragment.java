package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekAdapter;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for Trek list
 */
public class TrekListFragment extends ListFragment {

    private static TrekListFragment instance;

    public static TrekListFragment getInstance(DbHelper db) {
        Log.d("TREK_TrekListFragment", "Returning TrekListFragment -instance");

        // using singelton -pattern for treklist
        if (instance == null) {
            instance = new TrekListFragment();
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
        Log.d("TREK_TrekListFragment", "Building TrekList view");

        // setting page header content
        ImageView headerImageView
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_image);
        //TextView headerText
        //        = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_text);

        headerImageView.setImageResource(R.drawable.trek);
        //headerText.setText(R.string.term_treks);
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekListFragment", "Preparing TrekListView data");
        TrekAdapter adapter = new TrekAdapter(this.getActivity());

        // treklist contains treks from db
        adapter.setListRows(db.getTreks());
        this.listView.setAdapter(adapter);

        // this disables the child opening for treks - no need currently
        this.listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                return true;
            }
        });
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.TREK_LIST_ACTION_ID, view);
    }
}
