package com.betalife.sushibuffet.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AccessActivity extends Activity {
	private Timer timer;
	private TimerTask task;

	// 意图
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);
		intent = new Intent();
	}

	@Override
	protected void onStart() {
		super.onStart();
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {

				intent.setClass(AccessActivity.this, MainActivity.class);
				startActivity(intent);
			}
		};
		timer.schedule(task, 1000 * 5);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (null != timer) {
			timer.cancel();
			task.cancel();
		}
		Toast.makeText(AccessActivity.this, "MainActivity以暂停", Toast.LENGTH_LONG).show();
	}
}
