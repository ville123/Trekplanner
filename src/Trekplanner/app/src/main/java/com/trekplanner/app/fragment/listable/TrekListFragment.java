package com.trekplanner.app.fragment.listable;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.activity.MainActivity;
import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.listable.adapter.TrekAdapter;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Fragment for Trek list
 */
public class TrekListFragment extends ListFragment implements ListFragment.ListViewActionListener {

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
        headerImageView.setImageResource(R.drawable.treks);

        // hide actions from header
        TextView action1View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action1);
        action1View.setText("");

        TextView action2View
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_action2);
        action2View.setText("");

        // empty header background
        View headerLayout
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_layout);
        headerLayout.setBackgroundResource(0);

        ((AppCompatActivity)this.getActivity()).getSupportActionBar()
                .setTitle(getResources().getString(R.string.term_treks));

    }

    @Override
    protected void prepareListViewData() {
        Log.d("TREK_TrekListFragment", "Preparing TrekListView data");
        TrekAdapter adapter = new TrekAdapter(this.getActivity(), this);

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

    // floating button clicked, this case its to add new Trek
    @Override
    public void onClick(View view) {
        ((MainActivity) this.getActivity()).onListViewActionButtonClick(AppUtils.TREK_LIST_ACTION_ID, view);
    }

    @Override
    public void onForwardButtonClick(Object o) {
        // all UI navigation is handled by MainActivity
        ((MainActivity) this.getActivity()).onForwardButtonClick((Trek) o);
    }

    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        // no such funtion in trek list
    }

    @Override
    public void onDeleteButtonClicked(Object o) {
        // TODO: are we going to give user the possibility to delete treks?
    }

    // save action fired from treklistview
    @Override
    public void saveButtonClicked(Object o) {
        db.saveTrek((Trek) o);
        AppUtils.showOkMessage(getView(), R.string.phrase_save_success);
    }
}
