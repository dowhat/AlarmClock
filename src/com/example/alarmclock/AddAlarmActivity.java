package com.example.alarmclock;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

public class AddAlarmActivity extends BaseActivity implements OnClickListener {
	
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
	private String repeatTemp = "从不   >";
	private String ringTemp = "机械   >";
	private String tagTemp = "   >";
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
		setContentView(R.layout.add_clock);
		AdManager.getInstance(this).init("77b301b4a605a2bd", "6cc2fb144a5f3ded", false);
		//实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		//获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		//将广告条加入到布局中
		adLayout.addView(adView);
		ringData = (TextView) this.findViewById(R.id.ring_data);
		repeatData = (TextView) this.findViewById(R.id.repeat_data);
		tagData = (TextView) this.findViewById(R.id.tag_data);
		Button addAlarm = (Button) this.findViewById(R.id.title_edit);
		Button backAlarm = (Button) this.findViewById(R.id.title_back);
//		Button deleteAlarm = (Button) this.findViewById(R.id.delete_alarm);
		repeatData.setOnClickListener(this);
		ringData.setOnClickListener(this);
		tagData.setOnClickListener(this);
		backAlarm.setOnClickListener(this);
		addAlarm.setOnClickListener(this);
		timePicker = (TimePicker) findViewById(R.id.timePicker1);
		dbHelper = new ClockDatabaseHelper(this, "AlarmClock.db", null, 2);
		ringData.setText(ringTemp);
		repeatData.setText(repeatTemp);
		tagData.setText(tagTemp);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.repeat_data:
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
			AlertDialog.Builder ringDialog = new AlertDialog.Builder(AddAlarmActivity.this);
			ringDialog.setTitle("铃声");
			ringDialog.setCancelable(false);
			ringDialog.setSingleChoiceItems(ringCN, 0, new DialogInterface.OnClickListener() {
				
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
			AlertDialog.Builder tagDialog = new AlertDialog.Builder(AddAlarmActivity.this);
			tagDialog.setTitle("标签");
			tagDialog.setCancelable(false);
			tagDialog.setView(editText);
			tagDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tagBuf = editText.getText().toString();
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
			db.insert("AlarmClock", null, values);
			values.clear();
			Intent intent = new Intent(AddAlarmActivity.this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.title_back:
			Intent backIntent = new Intent(AddAlarmActivity.this, MainActivity.class);
			startActivity(backIntent);
		default:
			break;
		}
	}
	
}
