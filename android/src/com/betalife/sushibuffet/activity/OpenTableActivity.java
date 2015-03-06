package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.betalife.sushibuffet.adapter.OpenTableAdapter;
import com.betalife.sushibuffet.asynctask.GetAllTablesAsyncTask;
import com.betalife.sushibuffet.dialog.PasswordDialog;

public class OpenTableActivity extends TableActivity implements Callback {

	public OpenTableActivity() {
		adapter = new OpenTableAdapter(this);
		titleId = R.string.lbl_table_activity_title_choose_table;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PasswordDialog dialog = new PasswordDialog(this, this, false);
		dialog.show();
	}

	@Override
	public void callback() {
		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this, true);
		task.execute();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
