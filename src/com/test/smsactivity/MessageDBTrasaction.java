package com.test.smsactivity;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.test.smsactivity.MessageContract.MessageEntry;

public class MessageDBTrasaction {
	
	SQLiteDatabase messageDataBase;
	MessageOpenHelper messageHelper;
	
	Context conext;
	
	MessageDBTrasaction(Context conext){
		this.conext=conext;
		messageHelper=new MessageOpenHelper(conext);
		
		
	}
	
	public void insertIntoDatabase(MessageBean message){
		
		messageDataBase=messageHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(MessageEntry.COLUMN_NAME_ENTRY_ID, "123");
		values.put(MessageEntry.MESSAGE_FROM, message.getFrom());
		values.put(MessageEntry.MESSAGE_TEXT, message.getMessage());

		long newRowId;
		newRowId = messageDataBase.insert(
				MessageEntry.TABLE_NAME,
				null,
		         values);
		messageDataBase.close();

	}
	
public ArrayList<MessageBean> getMessages(){
		
		messageDataBase=messageHelper.getReadableDatabase();
		ArrayList<MessageBean> messages=new ArrayList<MessageBean>();
		
		Log.d("", "#### ");

		
		Cursor c = messageDataBase.query(
			    MessageEntry.TABLE_NAME,  // The table to query
			    MessageEntry.projection,                               // The columns to return
			    null,                                // The columns for the WHERE clause
			    null,                            // The values for the WHERE clause
			    null,                                     // don't group the rows
			    null,                                     // don't filter by row groups
			    MessageEntry.sortOrder                                 // The sort order
			    );
		
		c.moveToFirst();
		Log.d("", "#### "+c.isAfterLast());

		while(!c.isAfterLast()){
			
			String from = c.getString(c.getColumnIndex(MessageEntry.MESSAGE_FROM));
			String message = c.getString(c.getColumnIndex(MessageEntry.MESSAGE_TEXT));
			
			Log.d("", "#### "+from+"   "+message);
			MessageBean messageObject=new MessageBean(from, message);
			messages.add(messageObject);
			c.moveToNext();
		}
		c.close();
		messageDataBase.close();
		return messages;
	}

public boolean deleteMessage(String message) 
{
	boolean isDeleted;
	messageDataBase=messageHelper.getWritableDatabase();

    isDeleted= messageDataBase.delete(MessageEntry.TABLE_NAME, MessageEntry.MESSAGE_TEXT + "=" +"\""+ message+"\"", null) > 0;
    messageDataBase.close();
    
    return isDeleted;

}

}
