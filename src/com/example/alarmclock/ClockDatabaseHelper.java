package com.example.alarmclock;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class ClockDatabaseHelper extends SQLiteOpenHelper 
{
	public static final String CREATE_ALARMCLOCK = "create table AlarmClock ("
		+ "id integer primary key autoincrement, "
		+ "hour integer, "
		+ "minute integer, "
		+ "ring text, "
		+ "tag text, "
		+ "start integer, "
		+ "repeat integer, "
		+ "monday integer, "
		+ "tuesday integer, "
		+ "wednesday integer, "
		+ "thursday integer, "
		+ "friday integer, "
		+ "saturday integer, "
		+ "sunday integer)";
	
	public ClockDatabaseHelper(Context context, String name, CursorFactory factory, int version)
	{
		super(context, name, factory, version);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL(CREATE_ALARMCLOCK);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
	}

}