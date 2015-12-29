package com.example.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class EditAlarmActivity extends BaseActivity implements OnClickListener {
	
	private String[] weekDays = new String[]{"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
	private String[] weekDaySimple = new String[]{"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
	private String weekDaysE[]= {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
	private String[] rings = new String[]{"kalimba", "maid_with_the_flaxen_hair", "sleepaway"};
	private String[] ringCN = new String[]{"机械", "轻抚你的发", "忘忧"};
	private boolean[] weekData = new boolean[]{false, false, false, false, false, false, false};
	private ClockDatabaseHelper dbHelper;
	private int ringBuf = 0;
	private String tagBuf = "";
	private TimePicker timePicker;
	private long idTemp;
	private int currentRing = 0;
	private String repeatTemp = "";
	private String ringTemp = "";
	private String tagTemp = "";
	private TextView repeatData;
	private TextView ringData;
	private TextView tagData;
	
	
	@Override
	protected void onPause()
	{
		super.onPause();
		ActivityCollector.finishAll();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.edit_clock);
		repeatData = (TextView) this.findViewById(R.id.repeat_data);
		ringData = (TextView) this.findViewById(R.id.ring_data);
		tagData = (TextView) this.findViewById(R.id.tag_data);
		Button deleteAlarm = (Button) this.findViewById(R.id.delete_alarm);
		Button addAlarm = (Button) this.findViewById(R.id.title_edit);
		Button backAlarm = (Button) this.findViewById(R.id.title_back);
//		Button deleteAlarm = (Button) this.findViewById(R.id.delete_alarm);
		repeatData.setOnClickListener(this);
		ringData.setOnClickListener(this);
		tagData.setOnClickListener(this);
		addAlarm.setOnClickListener(this);
		backAlarm.setOnClickListener(this);
		deleteAlarm.setOnClickListener(this);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		dbHelper = new ClockDatabaseHelper(this, "AlarmClock.db", null, 2);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Intent intent = getIntent();
		long id = intent.getLongExtra("id", 0);
		Log.e("test", "get id = " + id);
		Cursor cursor = db.query("AlarmClock", null, null, null, null, null, null);
		cursor.moveToFirst();
		for (int i = 0; i < id; i++)
		{
			cursor.moveToNext();
		}
		idTemp = cursor.getInt(cursor.getColumnIndex("id"));
		timePicker.setCurrentHour(cursor.getInt(cursor.getColumnIndex("hour")));
		timePicker.setCurrentMinute(cursor.getInt(cursor.getColumnIndex("minute")));
		Log.e("test", Integer.toString(cursor.getInt(cursor.getColumnIndex("minute"))));
		Log.e("test", Integer.toString(cursor.getInt(cursor.getColumnIndex("hour"))));
		for (int i = 0; i < 7; i++)
		{
			if (cursor.getInt(cursor.getColumnIndex(weekDaysE[i])) == 1)
			{
				weekData[i] = true;
				repeatTemp += weekDaySimple[i] + " ";
			}
		}
		for (int i = 0; i < rings.length; i++)
		{
			if (rings[i].equals(cursor.getString(cursor.getColumnIndex("ring"))))
			{
				currentRing = i;
				ringTemp += ringCN[i];
			}
		}
		if (repeatTemp.equals(""))
		{
			repeatTemp += "从不";
		}
		repeatTemp += "  >";
		ringTemp += "  >";
		tagTemp += cursor.getString(cursor.getColumnIndex("tag")) + "  >";
		repeatData.setText(repeatTemp);
		ringData.setText(ringTemp);
		tagData.setText(tagTemp);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.repeat_data:
			AlertDialog.Builder repeatDialog = new AlertDialog.Builder(EditAlarmActivity.this);
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
					repeatTemp = "";
					for (int i = 0; i < 7; i++)
					{
						if (weekData[i] == true)
						{
							repeatTemp += weekDaySimple[i] + " ";
						}
					}
					repeatTemp += "  >";
					repeatData.setText(repeatTemp);
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
		case R.id.ring_data:
			AlertDialog.Builder ringDialog = new AlertDialog.Builder(EditAlarmActivity.this);
			ringDialog.setTitle("铃声");
			ringDialog.setCancelable(false);
			ringDialog.setSingleChoiceItems(ringCN, currentRing, new DialogInterface.OnClickListener() {
				
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
					ringData.setText(ringCN[ringBuf] + "  >");
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
		case R.id.tag_data:
			final EditText editText = new EditText(this);
			AlertDialog.Builder tagDialog = new AlertDialog.Builder(EditAlarmActivity.this);
			tagDialog.setTitle("标签");
			tagDialog.setCancelable(false);
			tagDialog.setView(editText);
			tagDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tagBuf = editText.getText().toString();
					Log.e("test", "tagBuf: " + tagBuf);
					tagData.setText(tagBuf + "  >");
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
			if (tagBuf != "")
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
			db.update("AlarmClock", values, "id = ?", new String[] { Long.toString(idTemp) });
			values.clear();
			Intent intent = new Intent(EditAlarmActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.title_back:
			Intent backIntent = new Intent(EditAlarmActivity.this, MainActivity.class);
			startActivity(backIntent);
			break;
		case R.id.delete_alarm:
			SQLiteDatabase dbt = dbHelper.getWritableDatabase();
			dbt.delete("AlarmClock", "id = ?", new String[] { Long.toString(idTemp) });
			Intent deleteIntent = new Intent(EditAlarmActivity.this, MainActivity.class);
			startActivity(deleteIntent);
			break;
		default:
			break;
		}
	}
	
}
