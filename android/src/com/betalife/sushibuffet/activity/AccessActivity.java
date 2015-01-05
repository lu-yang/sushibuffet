package com.betalife.sushibuffet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.betalife.sushibuffet.asynctask.ConstantAsyncTask;

public class AccessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);

		ConstantAsyncTask task = new ConstantAsyncTask(this);
		task.execute();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
