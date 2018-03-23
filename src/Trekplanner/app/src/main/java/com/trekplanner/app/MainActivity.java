package com.trekplanner.app;

import android.content.Intent;
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
import com.trekplanner.app.utils.AppUtils;

public class MainActivity extends AppCompatActivity implements ListFragment.ListViewActionListener {

    private DbHelper db;
    private Fragment itemListFragment;
    private Fragment trekListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        db = new DbHelper(this);

        itemListFragment = ItemListFragment.getInstance(db);
        trekListFragment = TrekListFragment.getInstance(db);

        Log.d("TREK_MainActivity", "opening splash screen");
        openSplashScreenActivity();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TREK_MainActivity", "opening item list");
        openItemList();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // action bar action selected
        Log.d("TREK_MainActivity", "Actionbar action selected");
        int id = item.getItemId();

        if (id == R.id.action_loaddefaults) {
            // TODO: create new db and load defaults from for example json file
            return true;
        } else if (id == R.id.action_cleardatabase) {
            // TODO: clear database (drop)
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("TREK_MainActivity", "Creating actionbar menu");
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    // Floating button clicked on some listview
    public void onListViewActionButtonClick(String listId, View view) {
        Log.d("TREK_MainActivity", "List view floating button clicked");
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

    private void openItemList(){
        openFragment(this.itemListFragment, false);
    }

    private void openTrekList() {

        openFragment(this.trekListFragment, false);
    }

    private void openItemPage(Item item) {
        openFragment(ItemEditFragment.getNewInstance(db, item), true);
    }

    private void openTrekPage(Trek trek) {
        //openFragment(TrekEditFragment.getInstance(db, trek), true);
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
        if (addToBackStack)  ft.addToBackStack(fragment.getClass().getName());
        ft.commit();

    }
}
