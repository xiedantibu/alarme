package com.huewu.alarme.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.huewu.alarme.R;
import com.huewu.alarme.db.AlarmePreference;
import com.huewu.alarme.model.AlarmInfo;
import com.huewu.alarme.model.GroupAlarmInfo;
import com.huewu.alarme.model.UserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;

/**
 * Fragment to represent new alarm setup screen.
 * User can do...
 * Check current time.
 * Choose private / group alarm.
 * Set new alarm time.
 * Add friends.
 * Request 'clock - app' sync.
 * Confirm a new alarm.
 * @author huewu.yang
 *
 */
public class SetAlarmFragment extends Fragment{
	
	private IAlarmeUIEvent mEventCallback = null;
	private TimePicker mTimePicker = null;
	private DatePicker mDatePicker = null;
	private Button mSetAlarm = null;
	private ImageButton mSetGroupAlarm = null;
	private ListView mMemberList = null;
	private String[] mMembers = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.set_alarm_frag, null);
		
		mSetAlarm = (Button) view.findViewById(R.id.set_alarm);
		mTimePicker = (TimePicker) view.findViewById(R.id.set_time);	
		mDatePicker = (DatePicker) view.findViewById(R.id.set_date);
		mSetGroupAlarm = (ImageButton) view.findViewById(R.id.group_alarm);
		mMemberList = (ListView) view.findViewById(R.id.group_members);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if( getActivity() instanceof IAlarmeUIEvent ){
			initAlarmUIEvent();
		}
		
		mMemberList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1));
	}
	
	private void initAlarmUIEvent() {
		mEventCallback = (IAlarmeUIEvent) getActivity();
		mSetAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//create AlarmInfo instance here? yes.
				
				Integer hour = mTimePicker.getCurrentHour();
				Integer min = mTimePicker.getCurrentMinute();
				Integer year = mDatePicker.getYear();
				Integer day = mDatePicker.getDayOfMonth();
				Integer month = mDatePicker.getMonth();
				
				UserInfo user = AlarmePreference.getUser(getActivity());
				
				Calendar cal = Calendar.getInstance();
				cal.set(year, month, day, hour, min, 0);
				
				//how to get current user here?
				
				if( mMembers == null || mMembers.length == 0 ){
					AlarmInfo newAlarm = new AlarmInfo(user, cal.getTimeInMillis());
					mEventCallback.onSetAlarm(newAlarm);
				}else{
					AlarmInfo newAlarm = new AlarmInfo(user, cal.getTimeInMillis());
					GroupAlarmInfo groupAlarm = new GroupAlarmInfo(newAlarm, mMembers);
					mEventCallback.onSetAlarm(groupAlarm);
				}
			}
		});
		
		mSetGroupAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mEventCallback.onSelectMember();
			}
		});		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mEventCallback = null;
	}

	public void updateMembers(ArrayList<String> members) {
		mMembers = members.toArray(new String[]{});
		
		((ArrayAdapter<String>)mMemberList.getAdapter()).clear();
		((ArrayAdapter<String>)mMemberList.getAdapter()).addAll(members);
	}

}//end of class
