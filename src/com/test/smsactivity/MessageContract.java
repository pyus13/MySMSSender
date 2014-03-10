package com.test.smsactivity;

import android.provider.BaseColumns;

public class MessageContract {

	
	public MessageContract() {}

    /* Inner class that defines the table contents */
    public static abstract class MessageEntry implements BaseColumns {
        public static final String TABLE_NAME = "allmessages";
        public static final String COLUMN_NAME_ENTRY_ID = "entryid";
        public static final String MESSAGE_FROM = "fromnumber";
        public static final String MESSAGE_TEXT = "message";
        
        
        
        public static final String TEXT_TYPE = " TEXT";
        public static final String COMMA_SEP = ",";
        
        
        public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY," +
            COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
            MESSAGE_FROM + TEXT_TYPE + COMMA_SEP +
            MESSAGE_TEXT + TEXT_TYPE +
           " )";

        public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
        
        
        public static String[] projection = {
        	    _ID,
        	    MESSAGE_FROM,
        	   MESSAGE_TEXT,
        	   
        	    };
        public static String sortOrder =
        	    MessageEntry._ID + " DESC";

    }

}