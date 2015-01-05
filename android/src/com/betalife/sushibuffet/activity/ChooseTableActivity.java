package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.view.KeyEvent;

import com.betalife.sushibuffet.adapter.AAdapter;
import com.betalife.sushibuffet.adapter.TableAdapter;
import com.betalife.sushibuffet.asynctask.GetAllTablesAsyncTask;
import com.betalife.sushibuffet.dialog.PasswordAlertDialog;
import com.betalife.sushibuffet.model.Diningtable;

public class ChooseTableActivity extends TableActivity implements PasswordAlertDialogCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		PasswordAlertDialog dialog = new PasswordAlertDialog(this, this);
		dialog.show();
	}

	@Override
	public void callback() {
		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this, true);
		task.execute();
	}

	@Override
	public AAdapter<Diningtable> getAdapter() {
		return new TableAdapter();
	}

	@Override
	public int getTitleId() {
		return R.string.lbl_table_activity_title_choose_table;
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
