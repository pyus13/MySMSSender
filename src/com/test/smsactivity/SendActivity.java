package com.test.smsactivity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class SendActivity extends Activity implements OnClickListener{

	private static final int MAX_SMS_MESSAGE_LENGTH = 160;
    private static final int SMS_PORT = 8901;
    private static final String SMS_DELIVERED = "SMS_DELIVERED";
    private static final String SMS_SENT = "SMS_SENT";

    
    private EditText phoneNumberField,messageField;
    private Button sendButton;
    private String message,phoneNumber;
    
    CheckBox isBinaryCheckBox;
    boolean isBinary;
    String from;
    Bundle bundle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		phoneNumberField=(EditText) findViewById(R.id.phonenumber);
		messageField=(EditText) findViewById(R.id.message);
		
		bundle=getIntent().getExtras();
		
		if(bundle!=null){
			
			from=bundle.getString("from");
			
			phoneNumberField.setText(from);
			messageField.requestFocus();
		
		}
		
		
		
		sendButton=(Button)findViewById(R.id.send);
		isBinaryCheckBox=(CheckBox)findViewById(R.id.binary);
		
		//comment this while testing on emulators
		isBinaryCheckBox.setChecked(true);
		isBinaryCheckBox.setVisibility(View.GONE);
		
		sendButton.setOnClickListener(this);
		
		registerReceiver(sendreceiver, new IntentFilter(SMS_SENT));
		
		registerReceiver(deliveredreceiver, new IntentFilter(SMS_DELIVERED));

		
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.send: 
			
			phoneNumber=phoneNumberField.getText().toString();
			message=messageField.getText().toString();
			if(TextUtils.isEmpty(phoneNumber)){
				Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
				
			}else if(TextUtils.isEmpty(message)){
				Toast.makeText(this, "Enter Some Text To send", Toast.LENGTH_SHORT).show();

				
			}else {
				
				isBinary=isBinaryCheckBox.isChecked();
				
				Log.d("", "Aradhna "+isBinary);
				sendSms(phoneNumber, message, isBinary);
			}
			
			
			
			break;

		default:
			break;
		}
		
	}
	
	
	 private void sendSms(String phonenumber,String message, boolean isBinary)
	    {
	        SmsManager manager = SmsManager.getDefault();
	       
	        PendingIntent piSend = PendingIntent.getBroadcast(this, 0, new Intent(SMS_SENT), 0);
	        PendingIntent piDelivered = PendingIntent.getBroadcast(this, 0, new Intent(SMS_DELIVERED), 0);
	       
	        Log.d("", "@@@@ "+isBinary);
	        
	        if(isBinary)
	        {
	                byte[] data = new byte[message.length()];
	               
	                for(int index=0; index<message.length() && index < MAX_SMS_MESSAGE_LENGTH; ++index)
	                {
	                        data[index] = (byte)message.charAt(index);
	                }
	               
	                manager.sendDataMessage(phonenumber, null, (short) SMS_PORT, data,piSend, piDelivered);
	                
	    	        Log.d("", "@@@@ "+data);

	        }
	        else
	        {
	                int length = message.length();
	               
	                if(length > MAX_SMS_MESSAGE_LENGTH)
	                {
	                        ArrayList<String> messagelist = manager.divideMessage(message);
	                       
	                        manager.sendMultipartTextMessage(phonenumber, null, messagelist, null, null);
	                }
	                else
	                {
	                        manager.sendTextMessage(phonenumber, null, message, piSend, piDelivered);
	                }
	        }
	    }

	
	 
	 
	 private BroadcastReceiver sendreceiver = new BroadcastReceiver()
     {
             @Override
             public void onReceive(Context context, Intent intent)
             {
                     String info = "Send information: ";
                    
                     switch(getResultCode())
                     {
                             case Activity.RESULT_OK: info += "send successful"; break;
                             case SmsManager.RESULT_ERROR_GENERIC_FAILURE: info += "send failed, generic failure"; break;
                             case SmsManager.RESULT_ERROR_NO_SERVICE: info += "send failed, no service"; break;
                             case SmsManager.RESULT_ERROR_NULL_PDU: info += "send failed, null pdu"; break;
                             case SmsManager.RESULT_ERROR_RADIO_OFF: info += "send failed, radio is off"; break;
                     }
                    
                     Toast.makeText(getBaseContext(), info, Toast.LENGTH_SHORT).show();

             }
     };
    
     private BroadcastReceiver deliveredreceiver = new BroadcastReceiver()
     {
             @Override
             public void onReceive(Context context, Intent intent)
             {
                     String info = "Delivery information: ";
                    
                     switch(getResultCode())
                     {
                             case Activity.RESULT_OK: info += "delivered"; break;
                             case Activity.RESULT_CANCELED: info += "not delivered"; break;
                     }
                    
                     Toast.makeText(getBaseContext(), info, Toast.LENGTH_SHORT).show();
             }
     };

	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_all, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.messagesmenu:
			
			startActivity(new Intent(SendActivity.this, AllMessages.class));
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
