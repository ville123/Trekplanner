package com.trekplanner.app;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekplanner.app.db.DbHelper;
import com.trekplanner.app.fragment.editable.ItemEditFragment;
import com.trekplanner.app.fragment.editable.MainEditFragment;
import com.trekplanner.app.fragment.editable.TrekEditFragment;
import com.trekplanner.app.fragment.listable.ItemListFragment;
import com.trekplanner.app.fragment.listable.ListFragment;
import com.trekplanner.app.fragment.listable.TrekItemListFragment;
import com.trekplanner.app.fragment.listable.TrekListFragment;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

/**
 * Created by Sami
 *
 * Main activity.
 *
 * Handles all the UI navigation actions
 */
public class MainActivity extends AppCompatActivity implements ListFragment.ListViewActionListener {

    private DbHelper db;
    private Fragment itemListFragment;
    private Fragment trekListFragment;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        //Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // this instace of dbHelper is used everywhere
        // no other instances should be created
        db = new DbHelper(this);

        // item and trek -list context will not change, only the content
        // thus singletons can be used
        itemListFragment = ItemListFragment.getInstance(db);
        trekListFragment = TrekListFragment.getInstance(db);

        Log.d("TREK_MainActivity", "opening splash screen");
        openSplashScreenActivity();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TREK_MainActivity", "opening item list");

        // TODO: for now item list is opened as default before the left menu is implemented
        openItemList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        Log.d("TREK_MainActivity", "Creating actionbar menu");
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);

        // by default the search is hidden
        menu.findItem(R.id.action_search).setVisible(false);

        // save the menu object for later use in showing search action where needed
        this.menu = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d("TREK_MainActivity", "Actionbar action selected");
        int id = item.getItemId();

        if (id == R.id.action_loaddefaults) {
            // TODO: create new db and load defaults from for example json file
            return true;
        } else if (id == R.id.action_cleardatabase) {
            // TODO: clear database (drop)
            return true;
        } else if (id == R.id.action_export) {
            // TODO: export items and treks to a json/csv/xml file
            return true;
        } else if (id == R.id.action_import) {
            // TODO: import items and treks from a json/csv/xml file
            return true;
        } else if (id == R.id.action_help) {
            // TODO: show help -page
            return true;
        } else if (id == R.id.action_settings) {
            // TODO: implement settings page
            // settings saved to db?
            return true;
        }

        // TODO: implement search for items

        return super.onOptionsItemSelected(item);
    }

    // Floating button clicked on some listview
    public void onListViewActionButtonClick(String listId, View view) {
        Log.d("TREK_MainActivity", "List view floating button clicked");

        // TODO: for now this action toggles between item and trek -lists
        // until -left menu is implemented

        // TODO: Item / trek editor should be opened here for creating new object

        if (listId.equals(AppUtils.ITEM_LIST_ACTION_ID)) {
            openTrekList();
        } else if (listId.equals(AppUtils.TREK_LIST_ACTION_ID)) {
            openItemList();
        } else if (listId.equals(AppUtils.TREKITEM_LIST_ACTION_ID)) {
            // TODO: open item selection list
            Snackbar.make(view, "Tästä pitäisi avautua varusteiden valintalista retkelle", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    // forward button clicked on Item or Trek list view
    @Override
    public void onForwardButtonClick(Object o) {
        Log.d("TREK_MainActivity", "listview row forward button clicked");
        if (o instanceof Item) {
            openItemPage((Item)o);
        } else if (o instanceof Trek) {
            openTrekPage((Trek)o);
        }
    }

    // add or substract count -button clicked on trekitem listview
    @Override
    public void onModifyCountButtonClicked(TrekItem trekItem) {
        db.saveTrekItem(trekItem);
    }

    // delete -button clicked on trekitem listview
    @Override
    public void onDeleteButtonClicked(Long rowId) {
        db.deleteTrekItem(rowId);
        // TODO: show OK or error message for user
    }

    // save action fired from itemlistview
    @Override
    public void saveButtonClicked(Item item) {

    }

    private void openItemList(){

        // TODO: maybe use action bar for view header?
        // getSupportActionBar().setTitle(R.string.term_items);

        // TODO: cant do this since openItemList() is called before menu is set
        //MenuItem item = this.menu.findItem(R.id.action_search);
        //item.setVisible(true);

        openFragment(this.itemListFragment, false);
    }

    private void openTrekList() {

        openFragment(this.trekListFragment, false);
    }

    private void openItemPage(Item item) {
        openFragment(ItemEditFragment.getNewInstance(db, item), true);
    }

    private void openTrekPage(Trek trek) {

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
        //TODO
    }

    private void openFragment(Fragment fragment, boolean addToBackStack) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frag_container, fragment);

        // if added to back stack, android back -button gets back to previous page
        if (addToBackStack)  ft.addToBackStack(fragment.getClass().getName());

        ft.commit();

    }
}
