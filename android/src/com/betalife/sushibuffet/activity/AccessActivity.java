package com.betalife.sushibuffet.activity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.util.DodoroContext;

public class AccessActivity extends Activity {

	// 意图

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();
		DodoroContext.locale(DodoroContext.DEFAULT_LOCALE, this);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_access);

		ConstantAsyncTask task = new ConstantAsyncTask(this);
		task.execute();
	}

	private class ConstantAsyncTask extends AbstractAsyncTask<Void, Constant> {

		public ConstantAsyncTask(Activity activity) {
			super(activity, false);
		}

		@Override
		public void postCallback(Constant result) {
			System.out.println(result);
			DodoroContext.getInstance().setConstant(result);
			Intent intent = new Intent();
			intent.setClass(AccessActivity.this, NewTableActivity.class);
			startActivity(intent);
		}

		@Override
		protected Constant doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/constants";
			System.out.println("url: " + url);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			ResponseEntity<Constant> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Constant.class);
			return responseEntity.getBody();
		}
	};
}
