package com.betalife.sushibuffet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.betalife.sushibuffet.asynctask.GetTakeawaysAsyncTask;
import com.betalife.sushibuffet.dialog.NewTakeawayDialog;
import com.betalife.sushibuffet.util.DodoroContext;

public class TakeAwayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_takeaway);

		GetTakeawaysAsyncTask task = new GetTakeawaysAsyncTask(this);
		task.execute();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DodoroContext.goTo(OpenTableActivity.class, this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void newTakeaway(View view) {
		NewTakeawayDialog dialog = new NewTakeawayDialog(this);
		dialog.show();
	}

}