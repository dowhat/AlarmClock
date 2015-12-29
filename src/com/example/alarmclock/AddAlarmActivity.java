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
	
	private String[] weekDays = new String[]{"����һ", "���ڶ�", "������", "������", "������", "������", "������"};
	private String[] weekDaySimple = new String[]{"��һ", "�ܶ�", "����", "����", "����", "����", "����"};
	private String weekDaysE[]= {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
	private String[] rings = new String[]{"kalimba", "maid_with_the_flaxen_hair", "sleepaway"};
	private String[] ringCN = new String[]{"��е", "�ḧ��ķ�", "����"};
	private boolean[] weekData = new boolean[]{false, false, false, false, false, false, false};
	private ClockDatabaseHelper dbHelper;
	private int ringBuf = 0;
	private String tagBuf = "";
	private TimePicker timePicker;
	private String repeatTemp = "�Ӳ�   >";
	private String ringTemp = "��е   >";
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
		//ʵ���������
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		//��ȡҪǶ�������Ĳ���
		LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
		//����������뵽������
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
			repeatDialog.setTitle("�ظ�");
			repeatDialog.setCancelable(false);
			repeatDialog.setMultiChoiceItems(weekDays, weekData, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// TODO Auto-generated method stub
					weekData[which] = isChecked;
				}
			});
			repeatDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
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
			repeatDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
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
			ringDialog.setTitle("����");
			ringDialog.setCancelable(false);
			ringDialog.setSingleChoiceItems(ringCN, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ringBuf = which;
				}
			});
			ringDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					ringData.setText(ringCN[ringBuf] + "  >");
				}
			});
			ringDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
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
			tagDialog.setTitle("��ǩ");
			tagDialog.setCancelable(false);
			tagDialog.setView(editText);
			tagDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					tagBuf = editText.getText().toString();
					tagData.setText(tagBuf + "  >");
				}
			});
			tagDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
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
