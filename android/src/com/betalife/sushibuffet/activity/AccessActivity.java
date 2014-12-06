package com.betalife.sushibuffet.activity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.TableAdapter;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.util.DodoroContext;

public class AccessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getActionBar().hide();

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
			DodoroContext.getInstance().setConstant(result);
			// DodoroContext.locale(DodoroContext.DEFAULT_LOCALE,
			// AccessActivity.this);
			// Resources resources = activity.getResources();
			// Configuration config = resources.getConfiguration();
			// config.locale = DodoroContext.DEFAULT_LOCALE;
			Intent intent = new Intent();
			Bundle mBundle = new Bundle();
			mBundle.putSerializable("adapter", new TableAdapter());
			intent.putExtras(mBundle);
			intent.setClass(AccessActivity.this, TableActivity.class);
			startActivity(intent);
		}

		@Override
		protected Constant doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/constant";
			System.out.println("url: " + url);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			ResponseEntity<Constant> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Constant.class);
			return responseEntity.getBody();
		}
	};
}
