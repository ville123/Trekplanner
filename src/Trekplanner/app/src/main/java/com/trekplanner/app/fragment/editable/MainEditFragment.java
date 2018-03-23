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
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.R;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.fragment.listable.adapter.TabAdapter;

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

        TextView headerText = view.findViewById(R.id.view_header_text);
        ImageView imageView = view.findViewById(R.id.view_header_image);

        // todo: if Item then item terms
        headerText.setText(R.string.term_trek);
        imageView.setImageResource(R.drawable.trek);

        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.term_data));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.term_items));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = view.findViewById(R.id.tabPage);

        AppCompatActivity context = (AppCompatActivity)this.getContext();

        final TabAdapter adapter = new TabAdapter
                (context.getSupportFragmentManager());

        adapter.addTabContent(this.editFragment);
        adapter.addTabContent(this.listFragment);

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
