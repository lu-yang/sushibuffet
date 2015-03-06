package com.betalife.sushibuffet.activity;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.betalife.sushibuffet.asynctask.GetTakeawaysAsyncTask;
import com.betalife.sushibuffet.asynctask.NewTakeawayTask;
import com.betalife.sushibuffet.model.Takeaway;
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
		EditText memoText = (EditText) findViewById(R.id.edittext_new_takeaway);
		String memo = memoText.getText().toString().trim();
		if (StringUtils.isEmpty(memo)) {
			Toast.makeText(this, getString(R.string.takeaway_activity_memo_empty_mes), Toast.LENGTH_SHORT)
					.show();
			return;
		}
		Takeaway takeaway = new Takeaway();
		takeaway.setMemo(memo);
		NewTakeawayTask task = new NewTakeawayTask(this);
		task.execute(takeaway);
	}

}