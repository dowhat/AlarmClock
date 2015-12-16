package com.example.alarmclock;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

	private ClockDatabaseHelper dbHelper;
	private List<AlarmInfo> alarmList = new ArrayList<AlarmInfo>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	dbHelper = new ClockDatabaseHelper(this, "AlarmClock.db", null, 2);
	ListView listView = (ListView) this.findViewById(R.id.clock_list);
	
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	// 查询Book表中所有的数据
	Cursor cursor = db.query("AlarmClock", null, null, null, null, null, null);
	getAlarmInfo(cursor);
	AlarmAdapter adapter = new AlarmAdapter(MainActivity.this, R.layout.clock_view, alarmList);
//	SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.clock_view, cursor, new String[]{"tag"}, new int[]{R.id.clock_tag});
	listView.setAdapter(adapter);
	// create database
	Button createDatabase = (Button) findViewById(R.id.create_database);
	createDatabase.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
	dbHelper.getWritableDatabase();
	}
	});
	
	// 创建数据库按钮
	Button addData = (Button) findViewById(R.id.add_data);
	addData.setOnClickListener(new OnClickListener() {
	@Override
	public void onClick(View v) {
	SQLiteDatabase db = dbHelper.getWritableDatabase();
	ContentValues values = new ContentValues();
	// 开始组装第一条数据
	values.put("hour", 11);
	values.put("minute", 30);
	values.put("ring", "gy");
	values.put("tag", "come on");
	values.put("start", 0);
	values.put("repeat", 1);
	db.insert("AlarmClock", null, values); // 插入第一条数据
	values.clear();
	// 开始组装第二条数据
	values.put("hour", 16);
	values.put("minute", 23);
	values.put("ring", "ange");
	values.put("tag", "come on");
	values.put("start", 1);
	values.put("repeat", 0);
	db.insert("AlarmClock", null, values); // 插入第二条数据
	Log.e("test", "add data");
	}
	});
	
//	// 数据更新按钮
//	Button updateData = (Button) findViewById(R.id.update_data);
//	updateData.setOnClickListener(new OnClickListener() {
//	@Override
//	public void onClick(View v) {
//	SQLiteDatabase db = dbHelper.getWritableDatabase();
//	ContentValues values = new ContentValues();
//	values.put("ring", "tiger");
//	db.update("AlarmClock", values, "tag = ?", new String[] { "come on" });
//	Log.e("test", "update data");
//	}
//	});
//	
//	// 删除数据按钮
//	Button deleteButton = (Button) findViewById(R.id.delete_data);
//	deleteButton.setOnClickListener(new OnClickListener() {
//	@Override
//	public void onClick(View v) {
//	SQLiteDatabase db = dbHelper.getWritableDatabase();
//	db.delete("AlarmClock", "hour > ?", new String[] { "10" });
//	Log.e("test", "delete data");
//	}
//	});
//	
//	// 数据查询按钮
//	Button queryButton = (Button) findViewById(R.id.query_data);
//	queryButton.setOnClickListener(new OnClickListener() {
//	@Override
//	public void onClick(View v) {
//	SQLiteDatabase db = dbHelper.getWritableDatabase();
//	// 查询Book表中所有的数据
//	Cursor cursor = db.query("AlarmClock", null, null, null, null, null, null);
//	if (cursor.moveToFirst()) {
//	do {
//	// 遍历Cursor对象，取出数据并打印
//	int hour = cursor.getInt(cursor.
//	getColumnIndex("hour"));
//	int minute = cursor.getInt(cursor.
//	getColumnIndex("minute"));
//	String ring = cursor.getString(cursor.getColumnIndex
//	("ring"));
//	String tag = cursor.getString(cursor.
//	getColumnIndex("tag"));
//	Log.e("MainActivity", "hour is " + hour);
//	Log.e("MainActivity", "minute is " + minute);
//	Log.e("MainActivity", "ring is " + ring);
//	Log.e("MainActivity", "tag is " + tag);
//	} while (cursor.moveToNext());
//	}
//	cursor.close();
//	}
//	});
//	
//	// 数据替换按钮
//	Button replaceData = (Button) findViewById(R.id.replace_data);
//	replaceData.setOnClickListener(new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//		SQLiteDatabase db = dbHelper.getWritableDatabase();
//		db.beginTransaction(); // 开启事务
//		try {
//		db.delete("AlarmClock", null, null);
////		if (true) {
////		// 在这里手动抛出一个异常，让事务失败
////		throw new NullPointerException();
////		}
//		ContentValues values = new ContentValues();
//		values.put("name", "Game of Thrones");
//		values.put("author", "George Martin");
//		values.put("pages", 720);
//		values.put("price", 20.85);
//		db.insert("AlarmClock", null, values);
//		db.setTransactionSuccessful(); // 事务已经执行成功
//		Log.e("test", "1111");
//		} catch (Exception e) {
//		e.printStackTrace();
//		} finally {
//		db.endTransaction(); // 结束事务
//		}
//		}
//		});
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
			int minute = cursor.getInt(cursor.
			getColumnIndex("minute"));
			String tag = cursor.getString(cursor.getColumnIndex
			("tag"));
			int start = cursor.getInt(cursor.getColumnIndex("start"));
			int repeat = cursor.getInt(cursor.getColumnIndex("repeat"));
			AlarmInfo alarmInfo = new AlarmInfo(hour, minute, tag, start, repeat);
			alarmList.add(alarmInfo);
			} while (cursor.moveToNext());
		}
	}
}
