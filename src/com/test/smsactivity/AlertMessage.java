package com.test.smsactivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlertMessage extends Activity{
	
	String from,message;

	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen);
		
		Log.d("", "@@@@@@@ "+getIntent().getExtras());
		
		bundle=getIntent().getExtras();
		
			
		from=bundle.getString("from");
		message=bundle.getString("message");
		
		Log.d("", "@@#### "+from+"  "+message);
		
		displayAlert();
	}
	
	
	private void displayAlert()
	{
	    AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    builder.setMessage("One message received. Do you want to read the message now ?").setCancelable(
	            false).setPositiveButton("Yes",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                    Intent intent=new Intent(AlertMessage.this, ReadMessage.class);
	                    intent.putExtra("from", from);
	                    intent.putExtra("message", message);
	                    startActivity(intent);
	                    
	                }
	            }).setNegativeButton("No",
	            new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int id) {
	                    dialog.cancel();
	                    finish();
	                }
	            });
	    AlertDialog alert = builder.create();
	    alert.show();
	}


}
