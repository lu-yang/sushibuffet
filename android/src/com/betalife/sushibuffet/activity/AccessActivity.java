package com.betalife.sushibuffet.activity;

import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.util.DodoroContext;

public class AccessActivity extends Activity {

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

	private class ConstantAsyncTask extends AbstractAsyncTask<Void, Map<String, Object>> {

		public ConstantAsyncTask(Activity activity) {
			super(activity, false);
		}

		@Override
		public void postCallback(Map<String, Object> result) {
			System.out.println(result);
			DodoroContext.getInstance().setConstants(result);
			intent.setClass(AccessActivity.this, NewTableActivity.class);
			startActivity(intent);
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
