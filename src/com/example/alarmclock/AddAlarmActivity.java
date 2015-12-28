package com.example.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddAlarmActivity extends Activity implements OnClickListener {
	
	private String[] weekDays = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
	private String weekDaysE[]= {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
	private String[] rings = new String[]{"kalimba", "maid_with_the_flaxen_hair", "sleepaway"};
	private boolean[] weekData = new boolean[]{false, false, false, false, false, false, false};
	private ClockDatabaseHelper dbHelper;
	private int ringBuf = 0;
	private String tagBuf;
	private TimePicker timePicker;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_clock);
		TextView repeat =(TextView) this.findViewById(R.id.repeat);
		TextView ring =(TextView) this.findViewById(R.id.ring);
		TextView tag =(TextView) this.findViewById(R.id.tag);
		Button addAlarm = (Button) this.findViewById(R.id.title_edit);
//		Button deleteAlarm = (Button) this.findViewById(R.id.delete_alarm);
		repeat.setOnClickListener(this);
		ring.setOnClickListener(this);
		tag.setOnClickListener(this);
		addAlarm.setOnClickListener(this);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		dbHelper = new ClockDatabaseHelper(this, "AlarmClock.db", null, 2);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.repeat:
			AlertDialog.Builder repeatDialog = new AlertDialog.Builder(AddAlarmActivity.this);
			repeatDialog.setTitle("重复");
			repeatDialog.setCancelable(false);
			repeatDialog.setMultiChoiceItems(weekDays, weekData, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// TODO Auto-generated method stub
					weekData[which] = isChecked;
				}
			});
			repeatDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
				}
			});
			repeatDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					for (int i = 0; i < weekDays.length; i++)
					{
						weekData[i] = false;
					}
				}
			});
			repeatDialog.show();
			break;
		case R.id.ring:
			AlertDialog.Builder ringDialog = new AlertDialog.Builder(AddAlarmActivity.this);
			ringDialog.setTitle("铃声");
			ringDialog.setCancelable(false);
			ringDialog.setSingleChoiceItems(rings, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ringBuf = which;
				}
			});
			ringDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			ringDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ringBuf = 0;
				}
			});
			ringDialog.show();
			break;
		case R.id.tag:
			AlertDialog.Builder tagDialog = new AlertDialog.Builder(AddAlarmActivity.this);
			LayoutInflater factory = LayoutInflater.from(AddAlarmActivity.this);
			final View DialogView = factory.inflate(R.layout.edit_text, null);
			tagDialog.setTitle("标签");
			tagDialog.setCancelable(false);
			tagDialog.setView(DialogView);
			tagDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tagBuf = ((EditText) findViewById(R.id.edit_text)).getText().toString();
				}
			});
			tagDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			tagDialog.show();
			break;
		case R.id.title_edit:
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("hour", timePicker.getCurrentHour());
			values.put("minute", timePicker.getCurrentMinute());
			values.put("start", 1);
			if (tagBuf != null)
			{
				values.put("tag", tagBuf);	
			}
			for (int i = 0; i < weekDays.length; i++)
			{
				if (weekData[i])
				{
					values.put(weekDaysE[i], 1);
					values.put("repeat", 1);
				}
			}
			values.put("ring", rings[ringBuf]);
			db.insert("AlarmClock", null, values);
			values.clear();
			Intent intent = new Intent(AddAlarmActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
}
