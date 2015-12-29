package com.example.alarmclock;

public class AlarmInfo {
	
	private int id;
	private int hour;
	private int minute;
	private String tag;
	private int start;
	private int repeat;

	public AlarmInfo(int id, int hour, int minute, String tag, int start, int repeat) {
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.tag = tag;
		this.start = start;
		this.repeat = repeat;
	}
	
	public int getId()
	{
		return id;
	}
	
	public int getHour()
	{
		return hour;
	}
	
	public int getMinute()
	{
		return minute;
	}
	public String getTag()
	{
		return tag;
	}
	public int getStart()
	{
		return start;
	}
	public int getRepeat()
	{
		return repeat;
	}
	
}