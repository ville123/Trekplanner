package com.trekplanner.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trekplanner.app.R;
import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.model.TrekItem;
import com.trekplanner.app.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by TBD on X.3.2018.
 */
public class DbHelper extends SQLiteOpenHelper {

    //Name of the database file
    private static final String DATABASE_NAME = "trekplanner.db";

    //Database version
    private static final int DATABASE_VERSION = 1;

    /**
     * Item tables names
     */
    public final static String ITEM_TABLE_NAME = "item";
    public final static String ITEM_TABLE2_NAME = "item2";

    //Unique ID number for the item (only in the database)
    //Type Integer
    public static final String COLUMN_ITEM_ID = "_ID";

    public static final String COLUMN_ITEM_TREK_ID = "trek_ID";

    //Type of the item
    public static final String COLUMN_ITEM_TYPE = "type";

    //Status of the item
    public static final String COLUMN_ITEM_STATUS = "status";

    //Weight of the item
    public static final String COLUMN_ITEM_WEIGHT = "weight";

    //name of the item
    public static final String COLUMN_ITEM_NAME = "name";

    //Notes about the item
    public static final String COLUMN_ITEM_NOTES = "notes";

    //pic of the item
    public static final String COLUMN_ITEM_PIC = "pic";

    //Default
    public static final String COLUMN_ITEM_DEFAULT = "mdefault";

    //Energy of the item
    public static final String COLUMN_ITEM_ENERGY = "energy";

    //Protein of the item
    public static final String COLUMN_ITEM_PROTEIN = "protein";

    //Datetime
    public static final String COLUMN_ITEM_DEADLINE = "datetime";

    /**
     * TREK TABLE
     */
    public final static String TREK_TABLE_NAME = "trek";

    public static final String COLUMN_TREK_ID = "_ID";

    public static final String COLUMN_TREK_START = "start";

    public static final String COLUMN_TREK_END = "end";

    public static final String COLUMN_TREK_SCOORDS = "startcoords";

    public static final String COLUMN_TREK_ECOORDS = "endcoords";

    public static final String COLUMN_TREK_DES = "description";

    public static final String COLUMN_TREK_NOTES = "notes";

    public static final String COLUMN_TREK_LENGTH = "length";

    public static final String COLUMN_TREK_LEVEL = "level";

    public static final String COLUMN_TREK_LESSONS = "lessonsLearnt";

    public static final String COLUMN_TREK_PIC = "pic";

    public static final String COLUMN_TREK_TOTALWEIGHT = "totalweight";

    /**
     * TREKITEM TABLE
     */
    public final static String TREKITEM_TABLE_NAME = "trekitems";

    public static final String COLUMN_TREKITEM_ID = "_ID";

    public static final String COLUMN_TREKITEM_ITEM_ID = "item_ID";

    public static final String COLUMN_TREKITEM_TREK_ID = "trek_ID";

    public static final String COLUMN_TREKITEM_COUNT= "count";

    public static final String COLUMN_TREKITEM_NOTES = "notes";

    public static final String COLUMN_TREKITEM_STATUS = "status";

    public static final String COLUMN_TREKITEM_WASUSED = "was_used";
    public static final String COLUMN_TREKITEM_COMMON = "is_common";
    public static final String COLUMN_TREKITEM_PRIVATE = "is_private";

    private final Context context;


    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL statements that creates items table
        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE IF NOT EXISTS " + ITEM_TABLE_NAME + " ("
                + COLUMN_ITEM_ID + " TEXT PRIMARY KEY, "
                + COLUMN_ITEM_TYPE + " TEXT NOT NULL, "
                + COLUMN_ITEM_STATUS + " TEXT NOT NULL, "
                + COLUMN_ITEM_WEIGHT + " REAL, "
                + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + COLUMN_ITEM_NOTES + " BLOB, "
                + COLUMN_ITEM_PIC + " BLOB, "
                + COLUMN_ITEM_DEFAULT + " INTEGER NOT NULL, "
                + COLUMN_ITEM_ENERGY + " REAL, "
                + COLUMN_ITEM_PROTEIN + " REAL, "
                + COLUMN_ITEM_DEADLINE + " TEXT );";

        //SQL statements that creates items2 table for trek specific items
        String SQL_CREATE_ITEM2_TABLE = "CREATE TABLE IF NOT EXISTS " + ITEM_TABLE2_NAME + " ("
                + COLUMN_ITEM_ID + " TEXT PRIMARY KEY, "
                + COLUMN_ITEM_TREK_ID + " TEXT NOT NULL, "
                + COLUMN_ITEM_TYPE + " TEXT NOT NULL, "
                + COLUMN_ITEM_STATUS + " TEXT NOT NULL, "
                + COLUMN_ITEM_WEIGHT + " REAL, "
                + COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + COLUMN_ITEM_NOTES + " BLOB, "
                + COLUMN_ITEM_PIC + " BLOB, "
                + COLUMN_ITEM_DEFAULT + " INTEGER NOT NULL, "
                + COLUMN_ITEM_ENERGY + " REAL, "
                + COLUMN_ITEM_PROTEIN + " REAL, "
                + COLUMN_ITEM_DEADLINE + " TEXT );";

        String SQL_CREATE_TREK_TABLE = "CREATE TABLE IF NOT EXISTS " + TREK_TABLE_NAME + " ("
                + COLUMN_TREK_ID + " TEXT PRIMARY KEY, "
                + COLUMN_TREK_START + " TEXT, "
                + COLUMN_TREK_END + " TEXT, "
                + COLUMN_TREK_SCOORDS + " TEXT, "
                + COLUMN_TREK_ECOORDS + " TEXT, "
                + COLUMN_TREK_DES + " TEXT NOT NULL, "
                + COLUMN_TREK_NOTES + " BLOB, "
                + COLUMN_TREK_LENGTH + " REAL, "
                + COLUMN_TREK_LEVEL + " TEXT NOT NULL, "
                + COLUMN_TREK_PIC + " BLOB, "
                + COLUMN_TREK_LESSONS + " TEXT, "
                + COLUMN_TREK_TOTALWEIGHT + " REAL );";

        String SQL_CREATE_TREKITEM_TABLE = "CREATE TABLE  IF NOT EXISTS " + TREKITEM_TABLE_NAME + " ("
                + COLUMN_TREKITEM_ID + " TEXT PRIMARY KEY, "
                + COLUMN_TREKITEM_ITEM_ID + " TEXT, "
                + COLUMN_TREKITEM_TREK_ID + " TEXT NOT NULL, "
                + COLUMN_TREKITEM_COUNT + " INTEGER NOT NULL, "
                + COLUMN_TREKITEM_NOTES + " BLOB, "
                + COLUMN_TREKITEM_STATUS + " TEXT NOT NULL, "
                + COLUMN_TREKITEM_PRIVATE + " INTEGER NOT NULL, "
                + COLUMN_TREKITEM_COMMON + " INTEGER NOT NULL, "
                + COLUMN_TREKITEM_WASUSED + " INTEGER  NOT NULL );";

        //Excute database statement
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM2_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TREK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TREKITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public void resetDefaults() {
        Log.d("TREK_DatabaseHelper", "Reseting defaults to db.");

        // first, drop tables
        dropDb();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // then create new db
        onCreate(this.getWritableDatabase());

        // then, read json and create objects to db
        String json = null;

        try {
            InputStream is = this.context.getAssets().open("defaults.json");

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject obj = new JSONObject(json);

            JSONArray items = obj.getJSONArray("items");
            List<Item> defaultItems = new ArrayList<>();

            for (int i = 0; i < items.length(); i++) {
                Item item = new Item();
                item.setName(items.getJSONObject(i).getString("name"));
                item.setType(items.getJSONObject(i).getString("type"));
                item.setStatus(items.getJSONObject(i).getString("status"));
                item.setWeight(items.getJSONObject(i).getDouble("weight"));
                item.setNotes(items.getJSONObject(i).getString("notes"));
                item.setPic(items.getJSONObject(i).getString("pic"));
                item.setDefault(items.getJSONObject(i).getInt("isDefault") == 1 ? true : false);
                item.setDeadline(items.getJSONObject(i).getString("deadline"));
                item.setId(saveItem(item));
                if (item.isDefault()) defaultItems.add(item);
            }

            JSONArray treks = obj.getJSONArray("treks");

            for (int i = 0; i < treks.length(); i++) {
                Trek trek = new Trek();
                trek.setDescription(treks.getJSONObject(i).getString("description"));
                trek.setEnd(treks.getJSONObject(i).getString("end"));
                trek.setStart(treks.getJSONObject(i).getString("start"));
                trek.setLength(treks.getJSONObject(i).getDouble("length"));
                trek.setEndCoords(treks.getJSONObject(i).getString("endCoords"));
                trek.setStartCoords(treks.getJSONObject(i).getString("startCoords"));
                trek.setLevel(treks.getJSONObject(i).getString("level"));
                trek.setNotes(treks.getJSONObject(i).getString("notes"));
                trek.setPic(treks.getJSONObject(i).getString("pic"));

                // select default items
                for (Item item : defaultItems) {
                    trek.addItem(item);
                }

                saveTrek(trek);
            }

        } catch (IOException e) {
            Log.e("TREK/DatabaseHelper.setUpDb.IO", e.getMessage());
            //e.printStackTrace();
        } catch (JSONException e) {
            Log.e("TREK/DatabaseHelper.setUpDb.JSON", e.getMessage());
            e.printStackTrace();
        }

    }

    public Trek selectRandomTrek() {

        Log.d("TREK_DatabaseHelper", "Selecting Random trek from db");

        List<Trek> treks = getTreks();

        return treks.get(ThreadLocalRandom.current().nextInt(0,  treks.size() + 1));
    }

    public void dropDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TREK_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TREKITEM_TABLE_NAME);
    }

    public String saveTrek(Trek trek) {

        SQLiteDatabase db = getWritableDatabase();
        trek.setTotalWeight(getItemsTotalWeight());
        boolean update = false;
        if (trek.getId() == null || trek.getId().isEmpty()) {
            trek.setId(AppUtils.generateUUID());
        } else {
            update = true;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_TREK_ID, trek.getId());
        values.put(COLUMN_TREK_START, trek.getStart());
        values.put(COLUMN_TREK_END, trek.getEnd());
        values.put(COLUMN_TREK_SCOORDS, trek.getStartCoords());
        values.put(COLUMN_TREK_ECOORDS, trek.getEndCoords());
        values.put(COLUMN_TREK_DES, trek.getDescription());
        values.put(COLUMN_TREK_NOTES, trek.getNotes());
        values.put(COLUMN_TREK_LENGTH, trek.getLength());
        values.put(COLUMN_TREK_LEVEL, trek.getLevel());
        values.put(COLUMN_TREK_LESSONS, trek.getLessonsLearned());
        values.put(COLUMN_TREK_PIC, trek.getPic());
        values.put(COLUMN_TREK_TOTALWEIGHT, trek.getTotalWeight());

        if(update){
            db.update(TREK_TABLE_NAME, values, COLUMN_TREK_ID + " = '" + trek.getId() + "'", null);
        } else {
            db.insert(TREK_TABLE_NAME,null, values);
        }

        if (!(trek.getItems() == null)) {
            for (Item item : trek.getItems()) {
                saveTrekItem(trek.getId(), item.getId());
            }
        }
        return trek.getId();
    }

    public String saveItem(Item item) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        boolean update = false;
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(AppUtils.generateUUID());
        } else {
            update = true;
        }

        // sanity
        if (item.getStatus()==null || item.getStatus().isEmpty())
            item.setStatus(context.getString(R.string.enum_itemstatus1));

        values.put(COLUMN_ITEM_ID, item.getId());
        values.put(COLUMN_ITEM_TYPE, item.getType());
        values.put(COLUMN_ITEM_STATUS, item.getStatus());
        values.put(COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_NOTES, item.getNotes());
        values.put(COLUMN_ITEM_PIC, item.getPic());
        values.put(COLUMN_ITEM_DEFAULT, (item.isDefault() != null && item.isDefault()?1:0));
        values.put(COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(COLUMN_ITEM_DEADLINE, item.getDeadline());

        if(update){
            db.update(ITEM_TABLE_NAME, values, COLUMN_ITEM_ID + " = '" + item.getId() + "'", null);
        } else {
            db.insert(ITEM_TABLE_NAME, null, values);
        }

        return item.getId();
    }


    public String saveTrekSpecificItem(Item item, String trekid) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        // TODO: copy-paste, nou nou !

        boolean update = false;
        if (item.getId() == null || item.getId().isEmpty()) {
            item.setId(AppUtils.generateUUID());
        } else {
            update = true;
        }

        // sanity
        if (item.getStatus()==null || item.getStatus().isEmpty())
            item.setStatus(context.getString(R.string.enum_itemstatus1));

        values.put(COLUMN_ITEM_ID, item.getId());
        values.put(COLUMN_ITEM_TREK_ID, trekid);
        values.put(COLUMN_ITEM_TYPE, item.getType());
        values.put(COLUMN_ITEM_STATUS, item.getStatus());
        values.put(COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_NOTES, item.getNotes());
        values.put(COLUMN_ITEM_PIC, item.getPic());
        values.put(COLUMN_ITEM_DEFAULT, (item.isDefault()!=null&&item.isDefault()?1:0));
        values.put(COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(COLUMN_ITEM_DEADLINE, item.getDeadline());

        if(update){
            db.update(ITEM_TABLE2_NAME, values, COLUMN_ITEM_ID + " = '" + item.getId() + "'", null);
        } else {
            db.insert(ITEM_TABLE2_NAME, null, values);

            // save also reference row to trekitem table
            TrekItem titem = new TrekItem();
            titem.setTrekId(trekid);
            titem.setItemId(item.getId());
            titem.setPrivate(true); // is trek private item
            saveTrekItem(titem);
        }

        return item.getId();

    }

    public String saveTrekItem(TrekItem titem) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        boolean update = false;
        if (titem.getId() == null || titem.getId().isEmpty()) {
            titem.setId(AppUtils.generateUUID());
        } else {
            update = true;
        }

        //sanitycheck
        if (titem.getStatus()==null || titem.getStatus().isEmpty())
            titem.setStatus(this.context.getString(R.string.enum_trekitemstatus1));
        if (titem.getWasUsed()==null)
            titem.setWasUsed(false);
        if (titem.getIsPrivate()==null)
            titem.setPrivate(false);
        if (titem.getIsCommon()==null)
            titem.setCommon(false);
        if (titem.getCount()==0)
            titem.setCount(1);

        values.put(COLUMN_TREKITEM_ID, titem.getId());
        values.put(COLUMN_TREKITEM_ITEM_ID, titem.getItemId());
        values.put(COLUMN_TREKITEM_TREK_ID, titem.getTrekId());
        values.put(COLUMN_TREKITEM_COUNT, titem.getCount());
        values.put(COLUMN_TREKITEM_NOTES, titem.getNotes());
        values.put(COLUMN_TREKITEM_STATUS, titem.getStatus());
        values.put(COLUMN_TREKITEM_WASUSED, (titem.getWasUsed()?1:0));
        values.put(COLUMN_TREKITEM_PRIVATE, (titem.getIsPrivate()?1:0));
        values.put(COLUMN_TREKITEM_COMMON, (titem.getIsCommon()?1:0));

        if(update){
            db.update(TREKITEM_TABLE_NAME, values, COLUMN_TREKITEM_ID + " = '" + titem.getId() + "'", null);
        }else{
            db.insert(TREKITEM_TABLE_NAME, null, values);
        }
        return titem.getId();
    }

    public void deleteItem(String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEM_TABLE_NAME, COLUMN_ITEM_ID + " = ?",
                new String[] { itemId });
    }

    public void deleteTrek(String trekId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TREK_TABLE_NAME, COLUMN_TREK_ID + " = ?",
                new String[] { trekId });
    }

    public void deleteTrekItem(TrekItem trekItem) {
        SQLiteDatabase db = this.getWritableDatabase();

        if (trekItem.getIsPrivate()) {
           //delete also from itemtable2
            db.delete(ITEM_TABLE2_NAME, COLUMN_ITEM_ID + "='" + trekItem.getItemId() + "'",
                    null);
        }

        db.delete(TREKITEM_TABLE_NAME, COLUMN_TREKITEM_ID + "='" + trekItem.getId() + "'",
                null);
    }

    public List<Item> getItems(String trekId, Integer sortOrder) {

        StringBuffer sortClause = new StringBuffer();
        sortClause.append(" ORDER BY ");

        if (sortOrder == AppUtils.SORT_ORDER_BY_TYPE) {
            sortClause.append(COLUMN_ITEM_TYPE);
        } else {
            sortClause.append(COLUMN_ITEM_NAME);
        }

        sortClause.append(" ASC");

        List<Item> items = new ArrayList<Item>();
        String selectQuery = null;

        if (trekId != null && !trekId.isEmpty()) {
            // do join
            selectQuery = "SELECT * FROM " + ITEM_TABLE_NAME +
                    " INNER JOIN " + TREKITEM_TABLE_NAME +
                    " ON " + ITEM_TABLE_NAME + "." + COLUMN_ITEM_ID + " = " + TREKITEM_TABLE_NAME + "." + COLUMN_TREKITEM_ITEM_ID +
                    " AND " + TREKITEM_TABLE_NAME + "." + COLUMN_TREKITEM_TREK_ID + "='" + trekId + "'" + sortClause;
        } else {
            selectQuery = "SELECT * FROM " + ITEM_TABLE_NAME + sortClause;
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Item item = new Item();
			item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID)));
            item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
            item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
            item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));
            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
            item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
            item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));

            int defaultAsInt = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT));
            item.setDefault(defaultAsInt==1?true:false);
            item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
            item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
            item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

            items.add(item);

            cursor.moveToNext();
        }

        return items;
    }

    public List<Trek> getTreks() {

        List<Trek> treks = new ArrayList<Trek>();
        String selectQuery = "SELECT * FROM " + TREK_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false) {
            Trek trek = new Trek();
            trek.setId(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_ID)));
            trek.setStart(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_START)));
            trek.setEnd(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_END)));
            trek.setStartCoords(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_SCOORDS)));
            trek.setEndCoords(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_ECOORDS)));
            trek.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_DES)));
            trek.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_NOTES)));
            trek.setLength(cursor.getDouble(cursor.getColumnIndex(COLUMN_TREK_LENGTH)));
            trek.setLevel(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_LEVEL)));
            trek.setLessonsLearned(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_LESSONS)));
            trek.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_PIC)));
            trek.setTotalWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_TREK_TOTALWEIGHT)));

            treks.add(trek);
            cursor.moveToNext();
        }

        return treks;
    }

    public List<TrekItem> getTrekItems(String trekId) {

        List<TrekItem> trekItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TREKITEM_TABLE_NAME + " WHERE " + COLUMN_TREKITEM_TREK_ID + "='" + trekId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false) {
            TrekItem trekItem = new TrekItem();
            trekItem.setId(cursor.getString(cursor.getColumnIndex(COLUMN_TREKITEM_ID)));
            trekItem.setItemId(cursor.getString(cursor.getColumnIndex(COLUMN_TREKITEM_ITEM_ID)));
            trekItem.setTrekId(cursor.getString(cursor.getColumnIndex(COLUMN_TREKITEM_TREK_ID)));
            trekItem.setCount(cursor.getInt(cursor.getColumnIndex(COLUMN_TREKITEM_COUNT)));
            trekItem.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_TREKITEM_NOTES)));
            trekItem.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_TREKITEM_STATUS)));

            int used = cursor.getInt(cursor.getColumnIndex(COLUMN_TREKITEM_WASUSED));
            trekItem.setWasUsed(used == 1?true:false);

            int priv = cursor.getInt(cursor.getColumnIndex(COLUMN_TREKITEM_PRIVATE));
            trekItem.setPrivate(priv==1?true:false);

            int comm = cursor.getInt(cursor.getColumnIndex(COLUMN_TREKITEM_COMMON));
            trekItem.setCommon(comm==1?true:false);

            Item item = null;
            if (trekItem.getIsPrivate()) {
                item = getTrekPrivateItem(trekItem.getItemId());
            } else {
                item = getItem(trekItem.getItemId());
            }
            trekItem.setItem(item);

            trekItems.add(trekItem);

            cursor.moveToNext();
        }

        return trekItems;
    }

    private Item getTrekPrivateItem(String itemId) {

        String selectQuery = "SELECT * FROM " + ITEM_TABLE2_NAME + " WHERE " + COLUMN_ITEM_ID + "='" + itemId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // TODO: totally copy-paste, nou nou!

        //Looping through all rows and adding to list
        Item item = new Item();
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID)));
            item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

            int isDef = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT));
            item.setDefault(isDef==1?true:false);

            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
            item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
            item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
            item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
            item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));
            item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
            item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
            item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));

            cursor.moveToNext();

        }
        return item;
    }

    private Item getItem(String itemId) {

        String selectQuery = "SELECT * FROM " + ITEM_TABLE_NAME + " WHERE " + COLUMN_ITEM_ID + "='" + itemId + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        Item item = new Item();
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID)));
            item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

            int isDef = cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT));
            item.setDefault(isDef==1?true:false);

            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
            item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
            item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
            item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
            item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));
            item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
            item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
            item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));

            cursor.moveToNext();

        }
        return item;
    }

    private void saveTrekItem(String trekId, String itemId) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_TREKITEM_ID, AppUtils.generateUUID());
        contentValues.put(COLUMN_TREKITEM_TREK_ID, trekId);
        contentValues.put(COLUMN_TREKITEM_ITEM_ID, itemId);
        contentValues.put(COLUMN_TREKITEM_COUNT, 1);
        contentValues.put(COLUMN_TREKITEM_STATUS, context.getString(R.string.enum_trekitemstatus1));
        contentValues.put(COLUMN_TREKITEM_WASUSED, 0);
        contentValues.put(COLUMN_TREKITEM_PRIVATE, 0);
        contentValues.put(COLUMN_TREKITEM_COMMON, 0);

        this.getWritableDatabase().insert(TREKITEM_TABLE_NAME, null, contentValues);
    }

    public List<Item> getItemListByKeyword(String search) {

        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> items = new ArrayList<Item>();

        String selectQuery =  "SELECT " +
                COLUMN_ITEM_ID + "," +
                COLUMN_ITEM_TYPE + "," +
                COLUMN_ITEM_STATUS + "," +
                COLUMN_ITEM_WEIGHT + "," +
                COLUMN_ITEM_NAME + "," +
                COLUMN_ITEM_NOTES + "," +
                COLUMN_ITEM_PIC + "," +
                COLUMN_ITEM_DEFAULT + "," +
                COLUMN_ITEM_ENERGY + "," +
                COLUMN_ITEM_PROTEIN + "," +
                COLUMN_ITEM_DEADLINE +
                " FROM " + ITEM_TABLE_NAME +
                " WHERE " +  COLUMN_ITEM_NAME + "  LIKE  '%" + search + "%' ";

        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            Item item = new Item();
            item.setId(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_ID)));
            item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
            item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
            item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));
            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
            item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
            item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));
            item.setDefault(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT)) != 1);
            item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
            item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
            item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

            items.add(item);

            cursor.moveToNext();
        }

        return items;
    }

    public Double getItemsTotalWeight() {

        SQLiteDatabase db = this.getReadableDatabase();

        double weight = 0;

        String selectQuery = "SELECT " +
                " SUM(" + "weight * " + COLUMN_TREKITEM_COUNT + ")" +
                " AS " + "Totalweight" +
                " FROM " + ITEM_TABLE_NAME +
                " INNER JOIN " + TREKITEM_TABLE_NAME +
                " ON " + ITEM_TABLE_NAME + "." + COLUMN_ITEM_ID + " = " +
                TREKITEM_TABLE_NAME + "." + COLUMN_TREKITEM_ITEM_ID;


        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        if(cursor.moveToFirst()){
            weight = cursor.getInt(0);
        }else{
            weight = -1;
        }

        return weight;
    }

    public void moveTrekPrivateItemToItems(TrekItem trekItem) {
        // get item from trek-private table
        Item privItem = getTrekPrivateItem(trekItem.getItemId());
        privItem.setId(null); // to create new item
        String newItemId = saveItem(privItem);
        trekItem.setPrivate(false);
        trekItem.setItemId(newItemId);
        saveTrekItem(trekItem);
        deleteTrekPrivateItem(trekItem.getId());
    }

    private void deleteTrekPrivateItem(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ITEM_TABLE2_NAME, COLUMN_ITEM_ID + "='" + id + "'",
                null);
    }
}
