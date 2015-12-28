package com.example.alarmclock;

import java.io.File;


import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	public AlarmReceiver() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		ClockDatabaseHelper dbHelper = new ClockDatabaseHelper(context, "AlarmClock.db", null, 2);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String weekDay[]= {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
		// 查询Book表中所有的数据
		Cursor cursor = db.query("AlarmClock", null, null, null, null, null, null);
		Time currentTime = new Time();
		currentTime.setToNow();
		int day = currentTime.weekDay;
		int hour = currentTime.hour;
		int minute = currentTime.minute;
		MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.kalimba);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			if (cursor.getInt(cursor.getColumnIndex("start")) == 1)
			{
				if (cursor.getInt(cursor.getColumnIndex("repeat")) == 1 && cursor.getColumnIndex(weekDay[day]) == 1)
				{
					Log.e("test", "时间到re");
					mediaPlayer.start();
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mediaPlayer.stop();
				}
				else
				{
					if (cursor.getInt(cursor.getColumnIndex("repeat")) != 1 && cursor.getInt(cursor.getColumnIndex("hour")) == hour && cursor.getInt(cursor.getColumnIndex("minute")) == minute)
					{
						Log.e("test", "shenmegui");
						Log.e("test", "时间到");
						mediaPlayer.start();
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						mediaPlayer.stop();
						SQLiteDatabase dbt = dbHelper.getWritableDatabase();
						ContentValues values = new ContentValues();
						values.put("start", 0);
						dbt.update("AlarmClock", values, "id = ?", new String[] { Integer.toString(cursor.getInt(cursor.getColumnIndex("id"))) });
					}
//					Log.e("test", Integer.toString(hour));
//					Log.e("test", Integer.toString(minute));
//					Log.e("test", Integer.toString(cursor.getInt(cursor.getColumnIndex("hour"))));
//					Log.e("test", Integer.toString(cursor.getInt(cursor.getColumnIndex("minute"))));
//					mediaPlayer.start();
				}
			}
		}
		
		
		
	}
	

}