package com.example.alarmclock;

import java.io.File;


import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.WindowManager;

public class AlarmReceiver extends BroadcastReceiver
{

	public AlarmReceiver()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onReceive(Context context, Intent intent)
	{
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
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
		{
			if (cursor.getInt(cursor.getColumnIndex("start")) == 1)
			{
				if (cursor.getInt(cursor.getColumnIndex("repeat")) == 1 && cursor.getColumnIndex(weekDay[day]) == 1)
				{
					MediaPlayer mediaPlayer = new MediaPlayer();
					Log.e("test", "时间到re");
					switch (cursor.getString(cursor.getColumnIndex("ring")))
					{
					case "kalimba":
						mediaPlayer = MediaPlayer.create(context, R.raw.kalimba);
						break;
					case "maid_with_the_flaxen_hair":
						mediaPlayer = MediaPlayer.create(context, R.raw.maid_with_the_flaxen_hair);
						break;
					case "sleepaway":
						mediaPlayer = MediaPlayer.create(context, R.raw.sleepaway);
						break;
					default:
						mediaPlayer = MediaPlayer.create(context, R.raw.kalimba);
					}
					mediaPlayer.start();
					try
					{
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
						MediaPlayer mediaPlayer = new MediaPlayer();
						Log.e("test", "shenmegui");
						Log.e("test", "时间到");
						switch (cursor.getString(cursor.getColumnIndex("ring")))
						{
						case "kalimba":
							mediaPlayer = MediaPlayer.create(context, R.raw.kalimba);
							break;
						case "maid_with_the_flaxen_hair":
							mediaPlayer = MediaPlayer.create(context, R.raw.maid_with_the_flaxen_hair);
							break;
						case "sleepaway":
							mediaPlayer = MediaPlayer.create(context, R.raw.sleepaway);
							break;
						default:
							mediaPlayer = MediaPlayer.create(context, R.raw.kalimba);
						}

						mediaPlayer.start();
						AlertDialog.Builder ringDialog = new AlertDialog.Builder(context);
						ringDialog.setTitle("时间到");
						ringDialog.setMessage("tag: ");
						ringDialog.setCancelable(false);
						ringDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
//								mediaPlayer.stop();
							}
						});
						ringDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								try {
									Thread.sleep(5000);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
//								mediaPlayer.stop();
							}
						});
						AlertDialog alertDialog = ringDialog.create();
						alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
						alertDialog.show();
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
				}
			}
		}
	}
}