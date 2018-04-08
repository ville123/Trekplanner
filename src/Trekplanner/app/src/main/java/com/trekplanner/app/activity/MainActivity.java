package com.trekplanner.app.activity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.trekplanner.app.R;
import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.PictureFragment;
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

import java.util.Map;

import static com.trekplanner.app.utils.AppUtils.REQUEST_IMAGE_CAPTURE;

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
    private PictureFragment pictureFragment;

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

        // item and trek -list context will not change, only the content
        // thus singletons can be used
        itemListFragment = ItemListFragment.getInstance(db);
        trekListFragment = TrekListFragment.getInstance(db);
        pictureFragment = PictureFragment.getInstance();

        Log.d("TREK_MainActivity", "opening splash screen");
        openSplashScreenActivity();

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

        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    AppUtils.REQUEST_IMAGE_CAPTURE);
        }

        Log.d("TREK_MainActivity", "opening item list");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppUtils.REQUEST_IMAGE_CAPTURE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // grant ok
            }
            else {
                // TODO: disable all camera features
            }
        }
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
        } else if (id == R.id.action_load_template) {
            // TODO: import trek from a json template
            AppUtils.showSelectionDialog(this,
                    null,
                    R.array.import_template_choices,
                    new ExportActionHandler(this));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // handle header image onclick -action
    public void showPicture(View view) {
        Drawable drawable = ((ImageView) view).getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        if (bitmap!=null) {
            this.pictureFragment.setPic(bitmap);
            openFragment(pictureFragment, true, true);
        }
    }

    // Floating button clicked on some listview
    public void onListViewFloatingButtonClick(Integer actionId, View view, Map<String, Object> attributes) {
        Log.d("TREK_MainActivity", "List view floating button clicked");

        if (actionId == AppUtils.ITEM_LIST_ACTION_ID) {
            Item item = new Item();
            item.setType((String)attributes.get(AppUtils.ITEM_TYPE_KEY)); // defaults to selected type
            item.setStatus(getResources().getString(R.string.enum_itemstatus1)); // default ok
            openItemPage(item);
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

        openFragment(this.itemListFragment, false, false);
    }

    private void openTrekList() {

        getSupportActionBar().setTitle(R.string.term_treks);
        menu.findItem(R.id.action_search).setVisible(false);

        openFragment(this.trekListFragment, false, false);
    }

    private void openItemPage(Item item) {
        menu.findItem(R.id.action_search).setVisible(false);
        openFragment(ItemEditFragment.getNewInstance(db, item), true, false);
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
                        TrekItemListFragment.getNewInstance(db, trek!=null?trek.getId():null)),
                true, false);
    }

    private void openSplashScreenActivity() {
        startActivity(new Intent(this, SplashActivity.class));
    }

    private void openFragment(Fragment fragment, boolean addToBackStack, boolean fullPage) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (fullPage) {
            ft.replace(android.R.id.content, fragment);
        } else {
            ft.replace(R.id.frag_container, fragment);
        }

        // if added to back stack, android back -button gets back to previous page
        if (addToBackStack)  ft.addToBackStack(fragment.getClass().getName());

        ft.commit();

    }

    private void doMySearch(String query){
        db.getItemListByKeyword(query);
    }

}