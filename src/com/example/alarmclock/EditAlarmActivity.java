package com.example.alarmclock;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class EditAlarmActivity extends Activity implements OnClickListener {
	
	private String[] weekDays = new String[]{"����һ", "���ڶ�", "������", "������", "������", "������", "������"};
	private String[] rings = new String[]{"kalimba", "maid_with_the_flaxen_hair", "sleepaway"};
	private boolean[] weekData = new boolean[]{false, false, false, false, false, false, false};
//	private RadioOnClick radioOnClick = new RadioOnClick(1);
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_clock);
		TextView repeat =(TextView) this.findViewById(R.id.repeat);
		TextView ring =(TextView) this.findViewById(R.id.ring);
		TextView tag =(TextView) this.findViewById(R.id.tag);
		Button deleteAlarm = (Button) this.findViewById(R.id.delete_alarm);
		repeat.setOnClickListener(this);
		ring.setOnClickListener(this);
		tag.setOnClickListener(this);
		deleteAlarm.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.repeat:
			AlertDialog.Builder repeatDialog = new AlertDialog.Builder(EditAlarmActivity.this);
			repeatDialog.setTitle("�ظ�");
			repeatDialog.setCancelable(false);
			repeatDialog.setMultiChoiceItems(weekDays, weekData, new DialogInterface.OnMultiChoiceClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					// TODO Auto-generated method stub
					
				}
			});
			repeatDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			repeatDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			repeatDialog.show();
			break;
		case R.id.ring:
			AlertDialog.Builder ringDialog = new AlertDialog.Builder(EditAlarmActivity.this);
			ringDialog.setTitle("����");
			ringDialog.setCancelable(false);
			ringDialog.setSingleChoiceItems(rings, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			ringDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			ringDialog.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			ringDialog.show();
			break;
		case R.id.tag:
			AlertDialog.Builder tagDialog = new AlertDialog.Builder(EditAlarmActivity.this);
			LayoutInflater factory = LayoutInflater.from(EditAlarmActivity.this);
			final View DialogView = factory.inflate(R.layout.edit_text, null);
			tagDialog.setTitle("��ǩ");
			tagDialog.setCancelable(false);
			tagDialog.setView(DialogView);
			tagDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
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

		}
	}
	
}
