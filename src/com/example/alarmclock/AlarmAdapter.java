package com.example.alarmclock;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmAdapter extends ArrayAdapter<AlarmInfo> {
	
	private int resourceId;
	private String time = "";
	private ClockDatabaseHelper dbHelper;

	public AlarmAdapter(Context context, int textViewResourceId,
			List<AlarmInfo> alarmList) {
		super(context, textViewResourceId, alarmList);
		dbHelper = new ClockDatabaseHelper(context, "AlarmClock.db", null, 2);
		resourceId = textViewResourceId;
	}
	
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	final AlarmInfo alarmInfo = getItem(position); // 获取当前项的Alarm实例
	View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
	TextView clockTime = (TextView) view.findViewById(R.id.clock_time);
	TextView clockRepeat = (TextView) view.findViewById(R.id.clock_repeat);
	TextView clockTag = (TextView) view.findViewById(R.id.clock_tag);
	CheckBox rememberPass = (CheckBox) view.findViewById(R.id.remember_pass);
	rememberPass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			// TODO Auto-generated method stub
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			if (isChecked)
			{
				values.put("start", "1");
			}
			else values.put("start", "0");
			db.update("AlarmClock", values, "id = ?", new String[]{Integer.toString(alarmInfo.getId())});
		}
	});
	if(alarmInfo.getHour() >= 10)
	{
		time += alarmInfo.getHour() + ":";
	}
	else
	{
		time += "0" + alarmInfo.getHour() + ":";
	}
	if(alarmInfo.getMinute() >= 10)
	{
		time += alarmInfo.getMinute() + ":00";
	}
	else
	{
		time += "0" + alarmInfo.getMinute() + ":00";
	}
	clockTime.setText(time);
	time = "";
	if(alarmInfo.getStart() ==0)
	{
		rememberPass.setChecked(false);
	}
	else
	{
		rememberPass.setChecked(true);
	}
	if(alarmInfo.getRepeat() == 0)
	{
		clockRepeat.setText("不重复");
	}
	else
	{
		clockRepeat.setText("重复运行");
	}
	clockTag.setText(alarmInfo.getTag());
	return view;
	}
}
