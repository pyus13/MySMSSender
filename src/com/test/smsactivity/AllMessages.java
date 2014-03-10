package com.test.smsactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class AllMessages extends Activity {

	ListView listView;
	MessageDBTrasaction trasaction;
	ArrayList<MessageBean> allMessages;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		trasaction=new MessageDBTrasaction(this);

		
		setContentView(R.layout.unread_messages_list);
		
		listView=(ListView) findViewById(R.id.allmessages);
		
		
		allMessages=trasaction.getMessages();
		setListViewItems();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
		allMessages=trasaction.getMessages();
		
		if(allMessages.size()==0){
			
			setContentView(R.layout.no_unread);
		}else{
			
			setContentView(R.layout.unread_messages_list);
			
			listView=(ListView) findViewById(R.id.allmessages);
		
			setListViewItems();
		
		}
	}
	
	private void setListViewItems(){
		MessageAdapter myAdapter=new MessageAdapter(this, R.layout.each_message_view, allMessages);

		listView.setAdapter(myAdapter);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View itemView, int arg2,
					long arg3) {
				
				Log.d("", "@@@@@######");

				TextView fromField=(TextView)itemView.findViewById(R.id.title);
				TextView textMessage=(TextView)itemView.findViewById(R.id.textmessage);
				
				String from=fromField.getText().toString();
				String message=textMessage.getText().toString();
				Intent intent=new Intent(AllMessages.this, ReadMessage.class);
                intent.putExtra("from", from);
                intent.putExtra("message", message);
                startActivity(intent);

				
			}
		});
		
	}
}
