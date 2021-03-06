package com.example.alarmclock;

import java.util.ArrayList;
import java.util.List;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends BaseActivity {

	private ClockDatabaseHelper dbHelper;
	private List<AlarmInfo> alarmList = new ArrayList<AlarmInfo>();
	private AlarmAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	Intent intent = new Intent(this, AlarmService.class);
	startService(intent);
	setContentView(R.layout.activity_main);
	dbHelper = new ClockDatabaseHelper(this, "AlarmClock.db", null, 2);
	ListView listView = (ListView) this.findViewById(R.id.clock_list);
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	// 查询Book表中所有的数据
	Cursor cursor = db.query("AlarmClock", null, null, null, null, null, null);
	getAlarmInfo(cursor);
	adapter = new AlarmAdapter(MainActivity.this, R.layout.clock_view, alarmList);
	listView.setAdapter(adapter);
	listView.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position ,long id)
		{
			AlarmInfo alarminfo = alarmList.get(position);
			Intent editIntent = new Intent(MainActivity.this, EditAlarmActivity.class);
			editIntent.putExtra("id", id);
			Log.e("test", "change id " + id);
			startActivity(editIntent);
		}
	});
	AdManager.getInstance(this).init("77b301b4a605a2bd", "6cc2fb144a5f3ded", false);
	//实例化广告条
	AdView adView = new AdView(this, AdSize.FIT_SCREEN);
	//获取要嵌入广告条的布局
	LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
	//将广告条加入到布局中
	adLayout.addView(adView);
	
	// add alarm clock
	Button addData = (Button) findViewById(R.id.title_add);
	addData.setOnClickListener(new OnClickListener()
	{
	@Override
	public void onClick(View v)
	{
		Intent intent = new Intent(MainActivity.this, AddAlarmActivity.class);
		startActivity(intent);
	}
	});
	
	}
	private void getAlarmInfo(Cursor cursor)
	{
		if (cursor.moveToFirst())
		{
			do
			{
				// 遍历Cursor对象
				int hour = cursor.getInt(cursor.
				getColumnIndex("hour"));
				int id = cursor.getInt(cursor.getColumnIndex("id"));
				int minute = cursor.getInt(cursor.
				getColumnIndex("minute"));
				String tag = cursor.getString(cursor.getColumnIndex
				("tag"));
				int start = cursor.getInt(cursor.getColumnIndex("start"));
				int repeat = cursor.getInt(cursor.getColumnIndex("repeat"));
				AlarmInfo alarmInfo = new AlarmInfo(id, hour, minute, tag, start, repeat);
				alarmList.add(alarmInfo);
			} while (cursor.moveToNext());
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		adapter.notifyDataSetChanged();
	}
//	private void getNextAlarm(Cursor cursor)
//	{
//		Long currentTime = SystemClock.elapsedRealtime();
//		Long triggerTime;
//		if (cursor.moveToFirst())
//		{
//			do
//			{
//			// 遍历Cursor对象
//			int hour = cursor.getInt(cursor.
//			getColumnIndex("hour"));
//			int minute = cursor.getInt(cursor.
//			getColumnIndex("minute"));
//			String tag = cursor.getString(cursor.getColumnIndex
//			("tag"));
//			String weekDay[]= {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
//			int enableDays[] = {0, 0, 0, 0, 0, 0, 0};
//			int start = cursor.getInt(cursor.getColumnIndex("start"));
//			int repeat = cursor.getInt(cursor.getColumnIndex("repeat"));
//			if (start == 1)
//			{
//				if (repeat == 0)
//				{
//					triggerTime =  
//				}
//			}
//			} while (cursor.moveToNext());
//		}
}

