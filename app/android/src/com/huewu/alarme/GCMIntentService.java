package com.huewu.alarme;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.huewu.alarme.db.AlarmePreference;

public class GCMIntentService extends GCMBaseIntentService {
	
	public static final String ACTION_ALARM_OFF = "ACTION_ALARM_OFF";
	public static final String ACTION_ALARM_REQUEST = "ACTION_ALARM_REQUEST";
	public static final String ACTION_ALARM_UPDATE = "ACTION_ALARM_UPDATE";

	@Override
	protected void onError(Context arg0, String arg1) {
		// Typically, there is nothing to be done other than evaluating the error (returned by errorId) and trying to fix the problem.
	}

	@Override
	protected void onMessage(Context arg0, Intent msg) {
		//should invoke callback method of Alarm Service. 
		//#1. first bind to alarm service.
		//#2. second call notify method.
		
		Log.d(TAG, "GCM Message: " + msg.toString() );
		
		//parse message.
		parseGCMMessage(msg);
	}

	@Override
	protected void onRegistered(Context ctx, String rid) {
		//Typically, you should send the regid to your server so it can use it to send messages to this device.
		//send GCM reg id to our clock server.
		
		//save rid here.
		AlarmePreference.setRegisterID(ctx, rid);
	}

	@Override
	protected void onUnregistered(Context ctx, String regID) {
		//Typically, you should send the regid to the server so it unregisters the device.
		AlarmePreference.setRegisterID(ctx, "");
	}
	
	private void parseGCMMessage(Intent msg){
		Log.d(TAG, "Bundle: " + msg.getExtras());
		String type = msg.getStringExtra("msg");
		
		if(type == null)
			return;
		
		if( type.equals("alarmOFF")){
			Intent i = new Intent(ACTION_ALARM_OFF);
			i.putExtras(msg.getExtras());
			sendBroadcast(i);
			//handle setOffAlarm.
		}else if( type.equals("")){
			//handle requestAlarm.
		}else if( type.equals("")){
			//handle updateAlarm.
		}
	}

}//end of class
