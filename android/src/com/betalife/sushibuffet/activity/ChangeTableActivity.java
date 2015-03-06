package com.betalife.sushibuffet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.betalife.sushibuffet.adapter.ChangeTableAdapter;
import com.betalife.sushibuffet.asynctask.GetAllTablesAsyncTask;

public class ChangeTableActivity extends TableActivity {

	public ChangeTableActivity() {
		adapter = new ChangeTableAdapter(this);
		titleId = R.string.lbl_table_activity_title_change_table;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this, true);
		task.execute();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent();
			intent.putExtra(MainActivity.SELECTED_NAVIGATION_INDEX, 3);
			intent.setClass(this, MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
