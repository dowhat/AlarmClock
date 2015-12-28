package com.example.alarmclock;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AlarmAdapter extends ArrayAdapter<AlarmInfo> {
	
	private int resourceId;
	private String time = "";

	public AlarmAdapter(Context context, int textViewResourceId,
			List<AlarmInfo> alarmList) {
		super(context, textViewResourceId, alarmList);
		resourceId = textViewResourceId;
	}
	
	public void notifyDataSetChanged()
	{
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	AlarmInfo alarmInfo = getItem(position); // 获取当前项的Alarm实例
	View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
	TextView clockTime = (TextView) view.findViewById(R.id.clock_time);
	TextView clockRepeat = (TextView) view.findViewById(R.id.clock_repeat);
	TextView clockTag = (TextView) view.findViewById(R.id.clock_tag);
	CheckBox rememberPass = (CheckBox) view.findViewById(R.id.remember_pass);
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
