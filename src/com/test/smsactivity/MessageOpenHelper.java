package com.test.smsactivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.smsactivity.MessageContract.MessageEntry;

public class MessageOpenHelper extends SQLiteOpenHelper {
	
	
	 public static final int DATABASE_VERSION = 2;
	    public static final String DATABASE_NAME = "FeedReader.db";

	    
	    public MessageOpenHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }

	    

	@Override
	public void onCreate(SQLiteDatabase db) {
        db.execSQL(MessageEntry.SQL_CREATE_ENTRIES);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL(MessageEntry.SQL_DELETE_ENTRIES);
	     onCreate(db);
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
