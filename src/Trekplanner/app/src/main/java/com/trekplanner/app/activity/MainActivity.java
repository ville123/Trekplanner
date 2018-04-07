package com.trekplanner.app.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.editable.ItemEditFragment;
import com.trekplanner.app.fragment.editable.MainEditFragment;
import com.trekplanner.app.fragment.editable.TrekEditFragment;
import com.trekplanner.app.fragment.listable.ItemListFragment;
import com.trekplanner.app.fragment.listable.TrekItemListFragment;
import com.trekplanner.app.fragment.listable.TrekListFragment;
import com.trekplanner.app.fragment.listable.adapter.ItemAdapter;
import com.trekplanner.app.handler.ExportActionHandler;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Main activity.
 *
 * Handles all the UI navigation actions
 */
public class MainActivity extends AppCompatActivity {

    private DbHelper db;
    private Fragment itemListFragment;
    private Fragment trekListFragment;
    private Menu menu;
    private DrawerLayout mDrawerLayout;
    private PreferenceActivity preferencesFragment;

    private ItemAdapter adapter;

    /**
     * item is only for testi
     */
    Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        // this instace of dbHelper is used everywhere
        // no other instances should be created
        db = new DbHelper(this);

        item = new Item();

        // item and trek -list context will not change, only the content
        // thus singletons can be used
        itemListFragment = ItemListFragment.getInstance(db);
        trekListFragment = TrekListFragment.getInstance(db);

        adapter = new ItemAdapter(MainActivity.this, null);

        Log.d("TREK_MainActivity", "opening splash screen");
        openSplashScreenActivity();

        // preferences fragment
        this.preferencesFragment = new PreferenceActivity();

        // Nav menu
        mDrawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here
                        if (menuItem.getItemId() == R.id.nav_first_fragment) {
                            Log.d("TREK_MainActivity", "First nav item selected");
                            openItemList();
                        } else if (menuItem.getItemId() == R.id.nav_second_fragment) {
                            Log.d("TREK_MainActivity", "Second nav item selected");
                            openTrekList();
                        }
                        return true;
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TREK_MainActivity", "opening item list");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d("TREK_MainActivity", "Creating actionbar menu");
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        // by default the search is hidden
        menu.findItem(R.id.action_search).setVisible(false);

        // save the menu object for later use in showing search action where needed
        this.menu = menu;

        openItemList();

        //TODO: Search not working at all
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                doMySearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                doMySearch(s);
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("TREK_MainActivity", "Actionbar action selected");
        int id = item.getItemId();

        if (id == R.id.action_loaddefaults) {
            db.resetDefaults();
            return true;
        } else if (id == R.id.action_export) {
            // TODO: export items and treks to a json/csv/xml file
            AppUtils.showSelectionDialog(this,
                    R.string.phrase_select_filesystem,
                    null,
                    R.array.filesystems,
                    new ExportActionHandler(this));
            return true;
        } else if (id == R.id.action_import) {
            // TODO: import items and treks from a json/csv/xml file
            return true;
        } else if (id == R.id.action_help) {
            // TODO: show help -page
            return true;
        } else if (id == R.id.action_settings) {
            openPreferences();
            return true;
        } else if (id == android.R.id.home){
            // Open navigation menu
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        // TODO: implement search for items

        return super.onOptionsItemSelected(item);
    }

    // Floating button clicked on some listview
    public void onListViewActionButtonClick(Integer actionId, View view) {
        Log.d("TREK_MainActivity", "List view floating button clicked");

        // TODO: for now this action toggles between item and trek -lists
        // until -left menu is implemented

        // TODO: Item / trek editor should be opened here for creating new object

        if (actionId == AppUtils.ITEM_LIST_ACTION_ID) {
            openItemPage(null);
        } else if (actionId == AppUtils.TREK_LIST_ACTION_ID) {
            //openItemList();
            openTrekPage(null);
        }
    }

    // forward button clicked on Item list view
    public void onForwardButtonClick(Item item) {
        Log.d("TREK_MainActivity", "Item listview row forward button clicked");
        openItemPage(item);
    }

    // forward button clicked on Trek list view
    public void onForwardButtonClick(Trek trek) {
        Log.d("TREK_MainActivity", "Trek listview row forward button clicked");
        openTrekPage(trek);
    }

    private void openItemList(){

        getSupportActionBar().setTitle(R.string.term_items);

        menu.findItem(R.id.action_search).setVisible(true);

        openFragment(this.itemListFragment, false);
    }

    private void openTrekList() {

        getSupportActionBar().setTitle(R.string.term_treks);
        menu.findItem(R.id.action_search).setVisible(false);

        openFragment(this.trekListFragment, false);
    }

    private void openItemPage(Item item) {
        menu.findItem(R.id.action_search).setVisible(false);
        openFragment(ItemEditFragment.getNewInstance(db, item), true);
    }

    private void openPreferences() {
        Intent intent = new Intent(this, PreferenceActivity.class);
        startActivity(intent);
    }

    private void openTrekPage(Trek trek) {

        menu.findItem(R.id.action_search).setVisible(false);

        // opening tab -layout by using MainEditFragment

        // since edit fragment context changes (some item / trek),
        // a new instances are always created

                openFragment(
                MainEditFragment.getNewInstance(
                        TrekEditFragment.getNewInstance(db, trek),
                        TrekItemListFragment.getNewInstance(db, trek.getId())),
                true);
    }

    private void openSplashScreenActivity() {
        startActivity(new Intent(this, SplashActivity.class));
    }

    private void openFragment(Fragment fragment, boolean addToBackStack) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, fragment);

        // if added to back stack, android back -button gets back to previous page
        if (addToBackStack)  ft.addToBackStack(fragment.getClass().getName());

        ft.commit();

    }

    private void doMySearch(String query){
        db.getItemListByKeyword(query);
        adapter.setListRows(db.getItems(null, AppUtils.SORT_ORDER_BY_NAME));
        adapter.notifyDataSetChanged();
    }
}
