package com.trekplanner.app.fragment.editable;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.trekplanner.app.R;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.fragment.listable.adapter.TabAdapter;

/**
 * Created by Sami
 *
 * Main fragment for item and trek edit fragments.
 * Handles tab-layout stuff
 */
public class MainEditFragment extends Fragment {

    private EditFragment editFragment;
    private ListFragment listFragment;

    public static MainEditFragment getNewInstance(EditFragment editFragment, ListFragment listFragment) {
        MainEditFragment instance = new MainEditFragment();
        instance.editFragment = editFragment;
        instance.listFragment = listFragment;
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("TREK_MainEditFragment", "creating tabulator view");

        View view = inflater.inflate(R.layout.editview_tab_layout, container, false);

        // setting page header content
        ImageView headerImageView
                = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.view_header_icon);

        headerImageView.setImageResource(R.drawable.trek);

        // show camerabutton
        ImageButton camBtn = this.getActivity().findViewById(android.R.id.content).findViewById(R.id.header_camera_button);
        camBtn.setVisibility(View.VISIBLE);

        // create tab layout and adapter
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.term_data));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.term_items));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.tabPage);

        AppCompatActivity context = (AppCompatActivity)this.getContext();

        final TabAdapter adapter = new TabAdapter
                (context.getSupportFragmentManager());

        // adapter shows editfrag for trek and listfrag for trekitems
        adapter.addTabContent(this.editFragment);
        adapter.addTabContent(this.listFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // TODO: find out replacement for deprecation
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }
}
