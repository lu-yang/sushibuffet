package com.betalife.sushibuffet.activity;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.betalife.sushibuffet.asynctask.ConstantAsyncTask;
import com.betalife.sushibuffet.dialog.ServerAddressAlertDialog;
import com.betalife.sushibuffet.util.DodoroContext;

public class AccessActivity extends Activity implements Callback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);

		String base_url = DodoroContext.getInstance().getServerAddress(this);
		if (StringUtils.isEmpty(base_url)) {
			ServerAddressAlertDialog dialog = new ServerAddressAlertDialog(this, this, false);
			dialog.show();
		} else {
			callback();
		}

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void callback() {
		ConstantAsyncTask task = new ConstantAsyncTask(this);
		task.execute();
	}

}
