package com.trekplanner.app.fragment.listable;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekListAdapter;
import com.trekplanner.app.utils.AppUtils;

public class TrekListFragment extends ListFragment {

    private static TrekListFragment instance;

    public static TrekListFragment getInstance(DbHelper db) {
        Log.d("TREK_TrekListFragment", "Returning TrekListFragment -instance");
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
        TextView headerText = view.findViewById(R.id.view_header_text);
        headerText.setText(R.string.term_treks);

        ImageView imageView = view.findViewById(R.id.view_header_image);
        imageView.setImageResource(R.drawable.trek);
    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekListFragment", "Preparing TrekListView data");
        TrekListAdapter adapter = new TrekListAdapter(this.getActivity());
        adapter.setListRows(db.getTreks());
        listView.setAdapter(adapter);
    }

    // floating button clicked
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.TREK_LIST_ACTION_ID, view);
    }
}
