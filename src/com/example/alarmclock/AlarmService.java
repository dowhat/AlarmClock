package com.example.alarmclock;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class AlarmService extends Service
{
	
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				Log.e("AlarmService", "executed at " + new Date().toString());
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int aMinute = 60 * 1000; // 这是10s的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime() + aMinute;
		Intent i = new Intent(this, AlarmReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
}
