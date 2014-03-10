package com.test.smsactivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ReadMessage extends Activity {
	
	TextView fromField,messageField;
	String from,message;
	Bundle bundle;
	Editor editor;
	SharedPreferences prefs;
	
	private final String MY_PASSWORD="aradhana";

	MessageDBTrasaction transaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_message);
		
		bundle=getIntent().getExtras();
		
		prefs=PreferenceManager.getDefaultSharedPreferences(this);
		editor=prefs.edit();
		from=bundle.getString("from");
		message=bundle.getString("message");
		
		Log.d("", "@@#### "+from+"  "+message);
		
		
		fromField=(TextView)findViewById(R.id.from);
		messageField=(TextView)findViewById(R.id.message);
		
		
		fromField.setText(from);
		messageField.setText(message);
		
		transaction=new MessageDBTrasaction(this);
		transaction.deleteMessage(message);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		 MenuInflater inflater=getMenuInflater();
		    inflater.inflate(R.menu.send, menu);


		   return true;//return true so to menu pop up is opens	
		  }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.feeds:
			
            AlertDialog.Builder alert = new AlertDialog.Builder(ReadMessage.this);
            LayoutInflater inflater=ReadMessage.this.getLayoutInflater();
            //this is what I did to added the layout to the alert dialog
            View layout=inflater.inflate(R.layout.pinscreen,null);       
            alert.setView(layout);
            final EditText passwordInput=(EditText)layout.findViewById(R.id.password);
            final Button okButton=(Button)layout.findViewById(R.id.okbutton);
            
            okButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					String passwordEntered=passwordInput.getText().toString();
					if(passwordEntered.equals(MY_PASSWORD)){
						
						finish();
						Intent replyIntent=new Intent(ReadMessage.this, SendActivity.class);
						
						replyIntent.putExtra("from", from);
						
						startActivity(replyIntent);
						
					}else{
						
			            Toast.makeText(ReadMessage.this, "Please Enter Correct Password", Toast.LENGTH_SHORT).show();
			            
			            
					}
					
		            
				}
			});
            
            alert.show();
			
			break;

		default:
			break;
		}
		
		
		return super.onOptionsItemSelected(item);
	}

}
