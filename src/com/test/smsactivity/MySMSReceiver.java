package com.test.smsactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;

public class MySMSReceiver extends BroadcastReceiver {

	String action,from,message;
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";
	
	SharedPreferences prefs;
	
	@Override
	public void onReceive(Context context, Intent intent) {

        Log.d("", "@@@@ Receiver"+intent.getDataString());

		 action=intent.getAction();
			prefs=PreferenceManager.getDefaultSharedPreferences(context);
        
			MessageDBTrasaction transaction=new MessageDBTrasaction(context);
			
        Bundle bundle = intent.getExtras();        
	        SmsMessage[] msgs = null;
	        
	       
	        if(null != bundle)
	        {
	                String info = "Binary SMS from ";
	            Object[] pdus = (Object[]) bundle.get("pdus");
	            msgs = new SmsMessage[pdus.length];
	            
	            if(action.equals(SMS_RECEIVED_ACTION)){
	            	
	            	 for (int i=0; i<msgs.length; i++){
	                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                        info += msgs[i].getOriginatingAddress();                    
	                        info += "\n*****TEXT MESSAGE*****\n";
	                        info += msgs[i].getMessageBody().toString();
	                        from=msgs[i].getOriginatingAddress();
	                        message=msgs[i].getMessageBody().toString();
	                      
	                        
	                    }

	            } else {
	            
	            
	            byte[] data = null;
	           
	            for (int i=0; i<msgs.length; i++){
	                msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);                
	                info += msgs[i].getOriginatingAddress();                    
	                info += "\n*****BINARY MESSAGE*****\n";
	                from= msgs[i].getOriginatingAddress(); 
	                data = msgs[i].getUserData();
	               
	                for(int index=0; index<data.length; ++index)
	                {
	                        info += Character.toString((char)data[index]);
	                        message += Character.toString((char)data[index]);
	                }
	                
                   
	            }
	            
	           }
	            
	            

		        Log.d("", "@@@@ "+message);
		        
		        if(!TextUtils.isEmpty(from) && !TextUtils.isEmpty(message)  ){
		        MessageBean messageObject=new MessageBean(from, message);
    			transaction.insertIntoDatabase(messageObject);
		        }
		        
	           // Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
	        }


		Intent showMessage=new Intent(context, AlertMessage.class);
		showMessage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		showMessage.putExtra("from", from);
		showMessage.putExtra("message", message);
		context.startActivity(showMessage);
		
	}

}
