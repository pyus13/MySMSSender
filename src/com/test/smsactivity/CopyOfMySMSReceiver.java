package com.test.smsactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class CopyOfMySMSReceiver extends BroadcastReceiver {

	String action,from,message;
	
	@Override
	public void onReceive(Context context, Intent intent) {


		 action=intent.getAction();
    			
        Bundle bundle = intent.getExtras();        
	    SmsMessage[] msgs = null;
	        
	       
	        if(null != bundle)
	        {
	            String info = "Binary SMS from ";
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];

	            byte[] data = null;
	           
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                info += msgs[i].getOriginatingAddress();                    
	                info += "\n*****BINARY MESSAGE*****\n";
	                from= msgs[i].getOriginatingAddress(); 
	                data = msgs[i].getUserData();
	               
	                for(int index=0; index<data.length; ++index) {
	                   info += Character.toString((char)data[index]);
	                   message += Character.toString((char)data[index]);
	                }
	            }
	            
	        }
	            	        
		Intent showMessage=new Intent(context, AlertMessage.class);
		showMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		showMessage.putExtra("from", from);
		showMessage.putExtra("message", message);
		context.startActivity(showMessage);
		
	}

}

