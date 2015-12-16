package com.example.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
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
		MediaPlayer mediaplayer = MediaPlayer.create(context, R.raw.kalimba);
		mediaplayer.stop();
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			if (cursor.getInt(cursor.getColumnIndex("start")) == 1)
			{
				if (cursor.getInt(cursor.getColumnIndex("repeat")) == 1 && cursor.getColumnIndex(weekDay[day]) == 1)
				{
					Log.e("test", "时间到re");
					mediaplayer.start();
				}
				else
				{
					if (cursor.getInt(cursor.getColumnIndex("hour")) == hour && cursor.getInt(cursor.getColumnIndex("minute")) == minute)
					{
						Log.e("test", "时间到");
						mediaplayer.start();
					}
					Log.e("test", "nothing");
				}
			}
		}
	}

}