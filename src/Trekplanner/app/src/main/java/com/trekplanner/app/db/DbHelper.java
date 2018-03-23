package com.trekplanner.app.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.trekplanner.app.model.Item;
import com.trekplanner.app.model.Trek;

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

        //Excute database statement
        sqLiteDatabase.execSQL(SQL_CREATE_ITEM_TABLE);

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

    public void addItem(Item item) {
    }

    public void addTrek(Trek trek) {
    }

    private void addTrekItem(Long trekId, Long itemId) {
    }

    public void saveTrek(Trek trek) {

    }

    public void saveItem(Item item) {
    }

}
