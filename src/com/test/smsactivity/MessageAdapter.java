package com.test.smsactivity;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MessageAdapter extends ArrayAdapter<MessageBean>{
	
	LayoutInflater myInflater;
	ArrayList<MessageBean> messages;

	public MessageAdapter(Context context, int resource, ArrayList<MessageBean> messages) {
		super(context, resource,  messages);
		this.messages=messages;
    	myInflater = LayoutInflater.from(getContext());
    	
	    Log.d("", "@@#$## ");


		// TODO Auto-generated constructor stub
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		  View v = convertView;

		    if (v == null) {

		        v = myInflater.inflate(R.layout.each_message_view, null);

		    }

		    MessageBean message = messages.get(position);
		    
		    Log.d("", "@@#$## "+message);

		    if (message != null) {

		        TextView from = (TextView) v.findViewById(R.id.title);
		        TextView text = (TextView) v.findViewById(R.id.textmessage);

		        if (from != null) {
		        	from.setText(message.getFrom());
		        }
		        if (text != null) {

		            text.setText(message.getMessage());
		        }
		       
		    }

		    return v;
	}
	
	
}
