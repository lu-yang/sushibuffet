package com.betalife.sushibuffet.activity;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.util.DodoroContext;

public class AccessActivity extends Activity {
	private ActionBar actionBar;
	private Timer timer;
	private TimerTask task;

	// 意图
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);
		intent = new Intent();

		ConstantAsyncTask task = new ConstantAsyncTask(this);
		task.execute();
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

	private class ConstantAsyncTask extends AbstractAsyncTask<Void, Map<String, Object>> {

		public ConstantAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(Map<String, Object> result) {
			System.out.println(result);
			DodoroContext.getInstance().setConstants(result);
		}

		@Override
		protected Map<String, Object> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/constants";
			System.out.println("url: " + url);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
					Map.class);
			return responseEntity.getBody();
		}
	};
}
