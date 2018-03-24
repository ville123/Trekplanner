package com.trekplanner.app.db;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;
import com.trekplanner.app.model.TrekItem;

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

    /** Item tables names
     *
     */
    public final static String TABLE_NAME = "item";

    //Unique ID number for the item (only in the database)
    //Type Integer
    public static final String _ID = "id";

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

    /**TREK TABLE
     */
    public final static String TREK_TABLE_NAME = "treks";

    public static final String TREK_ID = "id";

    public static final String COLUMN_TREK_START = "start";

    public static final String COLUMN_TREK_END = "end";

    public static final String COLUMN_TREK_SCOORDS = "startcoords";

    public static final String COLUMN_TREK_ECOORDS = "endcoords";

    public static final String COLUMN_TREK_DES = "description";

    public static final String COLUMN_TREK_NOTES = "notes";

    public static final String COLUMN_TREK_LENGTH = "length";

    public static final String COLUMN_TREK_LEVEL = "level";

    public static final String COLUMN_TREK_LESSONS = "lessonsLearnt";

    /**TREKITEM TABLE
     */
    public final static String TREKITEM_TABLE_NAME = "trekitems";

    public static final String TREKITEM_ID = "id";

    public static final String COLUMN_TREKITEM_ITEMS_ID = "item_ID";

    public static final String COLUMN_TREKITEM_TREK_ID = "trek_ID";

    public static final String COLUMN_TREKITEM_COUNT= "count";

    public static final String COLUMN_TREKITEM_NOTES = "notes";

    public static final String COLUMN_TREKITEM_TOTALWEIGHT = "totalweight";

    public static final String COLUMN_TREKITEM_STATUS = "status";

    public static final String COLUMN_TREKITEM_WASUSED = "was_used";


    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //SQL statements that creates items table
        String SQL_CREATE_ITEM_TABLE = "CREATE TABLE " + ItemContract.ItemEntry.TABLE_NAME + " ("
                + ItemContract.ItemEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_TYPE + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_STATUS + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_WEIGHT + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_NOTES + " TEXT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_PIC + " TEXT, "
                + ItemContract.ItemEntry.COLUMN_ITEM_DEFAULT + " INTEGER NOT NULL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_ENERGY + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_PROTEIN + " REAL, "
                + ItemContract.ItemEntry.COLUMN_ITEM_DEADLINE + " TEXT );";

        String SQL_CREATE_TREK_TABLE = "CREATE TABLE " + TREK_TABLE_NAME + " ("
                + TREK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TREK_START + " TEXT, "
                + COLUMN_TREK_END + " TEXT, "
                + COLUMN_TREK_SCOORDS + " TEXT, "
                + COLUMN_TREK_ECOORDS + " TEXT, "
                + COLUMN_TREK_DES + " TEXT NOT NULL, "
                + COLUMN_TREK_NOTES + " TEXT, "
                + COLUMN_TREK_LENGTH + " REAL, "
                + COLUMN_TREK_LEVEL + " TEXT, "
                + COLUMN_TREK_LESSONS + " TEXT );";

        String SQL_CREATE_TREKITEM_TABLE = "CREATE TABLE " + TREKITEM_TABLE_NAME + " ("
                + TREKITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TREKITEM_ITEMS_ID + " INTEGER, "
                + COLUMN_TREKITEM_TREK_ID + " INTEGER, "
                + COLUMN_TREKITEM_COUNT + " INTEGER NOT NULL, "
                + COLUMN_TREKITEM_NOTES + " TEXT, "
                + COLUMN_TREKITEM_TOTALWEIGHT + " REAL, "
                + COLUMN_TREKITEM_STATUS + " TEXT, "
                + COLUMN_TREKITEM_WASUSED + " INTEGER );";

        //Excute database statement
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TREK_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TREKITEM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public List<Item> getItems(Long trekId) {

        Item item = new Item();
        item.setDeadline("2018-04-15");
        item.setDefault(true);
        item.setEnergy(1600d);
        item.setName("Teltta");
        item.setNotes("Lorem Ipsum on yksinkertaisesti testausteksti, jota tulostus- ja ladontateollisuudet käyttävät. Lorem Ipsum on ollut teollisuuden normaali testausteksti jo 1500-luvulta asti, jolloin tuntematon tulostaja otti kaljuunan ja sekoitti sen tehdäkseen esimerkkikirjan. Se ei ole selvinnyt vain viittä vuosisataa, mutta myös loikan elektroniseen kirjoitukseen, jääden suurinpiirtein muuntamattomana. Se tuli kuuluisuuteen 1960-luvulla kun Letraset-paperiarkit, joissa oli Lorem Ipsum pätkiä, julkaistiin ja vielä myöhemmin tietokoneen julkistusohjelmissa, kuten Aldus PageMaker joissa oli versioita Lorem Ipsumista.");
        item.setPic("iVBORw0KGgoAAAANSUhEUgAAADUAAAA1CAYAAADh5qNwAAAEX0lEQVRoge2Zz28aRxTH90/wn+A/If9B/CdwYFZWC4oPRjnEUitzI1IdcWp6CVYPTk7h5p5ILrMraAFbAomoSEGy2Z1qg1iBIdYSDKVCSEj09QAzZn/YC+yALXWf9E6s2PnMe/N9b94Kgm+++eabb779z0yV0FNVFp+psnikSChvdvGNKotHqoSePvQ6XU2Vd7cVSXxTxainSCIs4rNnP6hycOeh12+yKQzKzy+2dvYCGqUYGBcJGF6lTN4lb+G68jPUzl5YIFH+UcApGP1AI0PS3//bKsdh9FUCGJQX8nEnB8ZFAkg6xOBUWTx6EJh6PrA1H51WOQ6TXmFhGKtPegUwLhLzafm5ng9sbRSoitHnaXRCMLxKrQxj9eFVCrQ/9jcLZgVyS7VhMwXXlV9gUD9dKmr0vG0ETJFQYlGgVjluEoKxkV0JTJFQfm1AqhzcoQt0A7quvLbJ9+RmuTM36RVYKipS8EfuQLO06ymSCN+UX11TzgqkF6MrnzFaz1R5d5srVBWLr6aLOwTj8hgUSYS/fn8GejEKw6ZZKBqlmA3K+swyPvd/Sc5Q0ygNr1IsEmr6O/hH/8224Nr5gQmor733pIjjTo79FzfRUDHao13C/M51yYnjImgktey+ZyB7tDidLUVCZ9MdT8LkprDQwV9WFNx8UD9lEs8FiqbeuJNjqVc7P+C66EWcbqZnoFnnDSQdMinbqmrmxWnd8tz00tqkFw/XDtXX3oOWiwDJhB3VkhZzFaM9rlDzacATqEtOTIrZKMVsz9CGt4rFV9yhtOy+59pjjZC1rhmXx5uFoi2Q024u65ObApBM2Abl1CeuFWrUxis1qU7u1FLdWf94QVUxejItpBHTC/RilF0OeabeXUDTdx7S5wKeoARBEJyEge4wyYRh1MaewFrlODRKMdc7F0mHZ5LOobGlHYX1pVRiSSbsOQ3d/Lb/Q33PQIJw26FbU21yU2DNa+38gHtr5HSeuHXqtKtQJNE2XBm1MZBMaO1gt6nHcYRWxeijIolgXCRsL5wH03KRletXoxRzPJ99LUlVr8INSBDcr/KjNjbdo1rl+NJRa/35E1xXXtvOEo0SF9WzGh260HuVY+5fHrOokUwY+l+SriIyNrJsQ1R51/QbLR1VjD5yBxKE2QATI92tPo2NLFsMdS0XYaOyYTMFg/opdC6PofnpJesoSCYEX/LPmcp2yVumeGsdk02LMeovUngH9VNolGIscve5XozC2MiylLVMo/innTOY+Dc7OwuMm0dtDF1yAo1SDPRilHmXnNjEYX5e6PmasSqYlo0s9VHgTvCvkulLyEaBqKny7nYVi5V5xRt3ckvDjDs5c7phpD/4Jx3acVBvfnoJXfLu3ugNmx+gS97ZBEWRUGKjXzvus9mHtwQVESch0ItR0LIRB6FAfUUSk1WMnjw0h6PV84Gt2ZwwSeXf2dGZIolJFaO9RxOZZayeD2ypcnBHlYM7jzYavvnmm2++PVb7Dzpe6BpM4ejiAAAAAElFTkSuQmCC");
        item.setProtein(10.4d);
        item.setStatus("1");
        item.setType("1");
        item.setWeight(2456d);

        List<Item> items = new ArrayList<>();
        items.add(item);

        return items;
    }

    public List<Trek> getTreks() {

        Trek trek = new Trek();
        trek.setDescription("Lumikenkäretki sallan IsoPyhä tunturille");
        trek.setEnd("2018-04-15");
        trek.setEndCoords("fakecoords");
        trek.setLength(34000d);
        trek.setLessonsLearned(null);
        trek.setLevel("1");
        trek.setNotes("Lorem Ipsum on yksinkertaisesti testausteksti, jota tulostus- ja ladontateollisuudet käyttävät. Lorem Ipsum on ollut teollisuuden normaali testausteksti jo 1500-luvulta asti, jolloin tuntematon tulostaja otti kaljuunan ja sekoitti sen tehdäkseen esimerkkikirjan. Se ei ole selvinnyt vain viittä vuosisataa, mutta myös loikan elektroniseen kirjoitukseen, jääden suurinpiirtein muuntamattomana. Se tuli kuuluisuuteen 1960-luvulla kun Letraset-paperiarkit, joissa oli Lorem Ipsum pätkiä, julkaistiin ja vielä myöhemmin tietokoneen julkistusohjelmissa, kuten Aldus PageMaker joissa oli versioita Lorem Ipsumista.");
        trek.setStart("2018-04-10");
        trek.setStartCoords("fakecoords");

        List<Trek> treks = new ArrayList<>();
        treks.add(trek);
        return treks;
    }

    /**
     *
     * @getContentResolver() not working because show error "Non-static method getContentResolver()
     * cannot be reference from a static context
     */
    //Inserting item to the database
    private void insertItem(Item item){
        //Gets the databse in write mode
        //SQLiteDatabase db = itemDbHelper.getWritableDatabase();

        //Create content values
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_TYPE, item.getType());
        values.put(COLUMN_ITEM_STATUS, item.getStatus());
        values.put(COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_NOTES, item.getNotes());
        values.put(COLUMN_ITEM_PIC, item.getPic());
        values.put(COLUMN_ITEM_DEFAULT, item.isDefault());
        values.put(COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(COLUMN_ITEM_DEADLINE, item.getDeadline());

        // Insert a new item into the provider, returning the content URI for the new item.
        //Todo not working code, looking for solution
        //Context.getContentResolver().insert(ItemContract.ItemEntry.CONTENT_URI, values);

    }

    public void addItem(Item item) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_TYPE, item.getType());
        values.put(COLUMN_ITEM_STATUS, item.getStatus());
        values.put(COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_NOTES, item.getNotes());
        values.put(COLUMN_ITEM_PIC, item.getPic());
        values.put(COLUMN_ITEM_DEFAULT, item.isDefault());
        values.put(COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(COLUMN_ITEM_DEADLINE, item.getDeadline());

        db.insert(TABLE_NAME, null, values);
    }

    public void addTrek(Trek trek) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TREK_START, trek.getStart());
        values.put(COLUMN_TREK_END, trek.getEnd());
        values.put(COLUMN_TREK_SCOORDS, trek.getStartCoords());
        values.put(COLUMN_TREK_ECOORDS, trek.getEndCoords());
        values.put(COLUMN_TREK_DES, trek.getDescription());
        values.put(COLUMN_TREK_NOTES, trek.getNotes());
        values.put(COLUMN_TREK_LENGTH, trek.getLength());
        values.put(COLUMN_TREK_LEVEL, trek.getLevel());
        values.put(COLUMN_TREK_LESSONS, trek.getLessonsLearned());

        db.insert(TREK_TABLE_NAME, null, values);
    }

    private void addTrekItem(Long trekId, Long itemId) {

        SQLiteDatabase db = this.getWritableDatabase();

        TrekItem trekItem = new TrekItem();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TREKITEM_ITEMS_ID, itemId);
        values.put(COLUMN_TREKITEM_TREK_ID, trekId);
        values.put(COLUMN_TREKITEM_COUNT, trekItem.getCount());
        values.put(COLUMN_TREKITEM_NOTES, trekItem.getNotes());
        values.put(COLUMN_TREKITEM_TOTALWEIGHT, trekItem.getTotalWeight());
        values.put(COLUMN_TREKITEM_STATUS, trekItem.getStatus());
        values.put(COLUMN_TREKITEM_WASUSED, trekItem.getWas_used());

        db.insert(TREKITEM_TABLE_NAME, null, values);
    }

    public void saveTrek(Trek trek) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TREK_START, trek.getStart());
        values.put(COLUMN_TREK_END, trek.getEnd());
        values.put(COLUMN_TREK_SCOORDS, trek.getStartCoords());
        values.put(COLUMN_TREK_ECOORDS, trek.getEndCoords());
        values.put(COLUMN_TREK_DES, trek.getDescription());
        values.put(COLUMN_TREK_NOTES, trek.getNotes());
        values.put(COLUMN_TREK_LENGTH, trek.getLength());
        values.put(COLUMN_TREK_LEVEL, trek.getLevel());
        values.put(COLUMN_TREK_LESSONS, trek.getLessonsLearned());

        db.update(TREK_TABLE_NAME, values, TREK_ID + " = ?", null);
    }

    public void saveItem(Item item) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_TYPE, item.getType());
        values.put(COLUMN_ITEM_STATUS, item.getStatus());
        values.put(COLUMN_ITEM_WEIGHT, item.getWeight());
        values.put(COLUMN_ITEM_NAME, item.getName());
        values.put(COLUMN_ITEM_NOTES, item.getNotes());
        values.put(COLUMN_ITEM_PIC, item.getPic());
        values.put(COLUMN_ITEM_DEFAULT, item.isDefault());
        values.put(COLUMN_ITEM_ENERGY, item.getEnergy());
        values.put(COLUMN_ITEM_PROTEIN, item.getProtein());
        values.put(COLUMN_ITEM_DEADLINE, item.getDeadline());

        db.update(TABLE_NAME, values, _ID + " = ?", null);
    }

    /**
     * Deleting an item
     */
    public void deleteItem(long item_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, _ID + " = ?",
                new String[] { String.valueOf(item_id) });
    }

    public void deleteTrek(long trek_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TREK_TABLE_NAME, TREK_ID + " = ?",
                new String[] { String.valueOf(trek_id) });
    }

    //get single item
    public Item getSingleItem(long item_id){
        SQLiteDatabase db = getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + _ID + " = " + item_id;

        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor != null)
            cursor.moveToFirst();

        Item item = new Item();
        item.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
        item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
        item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));
        item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
        item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
        item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));
        item.setDefault(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT)) > 0);
        item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
        item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
        item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

        return item;
    }

    //Getting all items
    public List<Item> getAllItems(Long itemId) {

        List<Item> items = new ArrayList<Item>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + _ID + " = " + itemId;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        while(cursor.moveToFirst()){
            Item item = new Item();
            item.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
            item.setType(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_TYPE)));
            item.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_STATUS)));
            item.setWeight(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_WEIGHT)));
            item.setName(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NAME)));
            item.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_NOTES)));
            item.setPic(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_PIC)));
            item.setDefault(cursor.getInt(cursor.getColumnIndex(COLUMN_ITEM_DEFAULT)) > 0);
            item.setEnergy(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_ENERGY)));
            item.setProtein(cursor.getDouble(cursor.getColumnIndex(COLUMN_ITEM_PROTEIN)));
            item.setDeadline(cursor.getString(cursor.getColumnIndex(COLUMN_ITEM_DEADLINE)));

            items.add(item);
        }

        return items;
    }

    //Getting all items
    public List<Trek> getAllTreks() {

        List<Trek> treks = new ArrayList<Trek>();
        String selectQuery = "SELECT * FROM " + TREK_TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //Looping through all rows and adding to list
        while(cursor.moveToFirst()){
            Trek trek = new Trek();
            trek.setId(cursor.getLong(cursor.getColumnIndex(TREK_ID)));
            trek.setStart(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_START)));
            trek.setEnd(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_END)));
            trek.setStartCoords(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_SCOORDS)));
            trek.setEndCoords(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_ECOORDS)));
            trek.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_DES)));
            trek.setNotes(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_NOTES)));
            trek.setLength(cursor.getDouble(cursor.getColumnIndex(COLUMN_TREK_LENGTH)));
            trek.setLevel(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_LEVEL)));
            trek.setLessonsLearned(cursor.getString(cursor.getColumnIndex(COLUMN_TREK_LESSONS)));

            treks.add(trek);
        }

        return treks;
    }
}
