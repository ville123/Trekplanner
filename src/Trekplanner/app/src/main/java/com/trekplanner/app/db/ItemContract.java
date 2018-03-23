package com.trekplanner.app.db;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Shakur on 23.3.2018.
 */

public final class ItemContract {

    /**
     * Empty constructor.
     **/
    private ItemContract(){}

    /**
     * The "Content Authority" is the package name of the app which is unique
     */
    public static final String CONTENT_AUTHORITY = "com.trekplanner.app";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.trekplanner.app.db/db
     */
    public static final String PATH_TREK = "items";

    public static final class ItemEntry implements BaseColumns {

        /** The content URI to access the item data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_TREK);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of planner items.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TREK;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single item.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TREK;

        /**Name of the database table for items
         */
        public final static String TABLE_NAME = "items";

        /**Unique ID number for the item (only in the database)
         Type Integer
         */
        public static final String _ID = BaseColumns._ID;

        //Type of the item
        public static final String COLUMN_ITEM_TYPE = "type";

        /**Status of the item
         */
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
    }
}
