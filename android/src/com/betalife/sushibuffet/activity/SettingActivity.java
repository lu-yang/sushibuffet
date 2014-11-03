package com.betalife.sushibuffet.activity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class SettingActivity extends Activity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		actionBar = getActionBar();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// 设置ActionBar左边默认的图标是否可用
		actionBar.setDisplayUseLogoEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		// 设置ActionBar标题不显示
		actionBar.setDisplayShowTitleEnabled(false);
		// 设置ActionBar自定义布局
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(R.layout.abs_layout);
		// 设置导航模式为Tab选项标签导航模式
		View homeIcon = findViewById(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ? android.R.id.home
				: R.id.FragmentHeader);
		((View) homeIcon.getParent()).setVisibility(View.GONE);
		((View) homeIcon).setVisibility(View.GONE);
	}

	public void checkout(View view) {
		CheckoutTask task = new CheckoutTask(this);
		task.execute(DodoroContext.getInstance().getTurnover());
	}

	private class CheckoutTask extends AbstractAsyncTask<Turnover, Boolean> {

		public CheckoutTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Boolean doInBackground(Turnover... params) {
			Turnover tur = params[0];
			final String url = activity.getString(R.string.base_uri) + "/checkout/" + tur.getId();
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Boolean.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Boolean result) {
			DodoroContext.getInstance().setTurnover(null);

			Intent intent = new Intent();
			intent.setClass(activity, AccessActivity.class);
			activity.startActivity(intent);
		}
	}

	public void changeTable(View view) {
		// // TODO test
		// ChangeTableTask task = new ChangeTableTask(this);
		// Turnover turnover = DodoroContext.getInstance().getTurnover();
		// turnover.setTableId(turnover.getTableId() + 2);
		// task.execute(turnover);

		Intent intent = new Intent();
		intent.setClass(this, ChangeTableActivity.class);
		startActivity(intent);
	}

	private class ChangeTableTask extends AbstractAsyncTask<Turnover, Boolean> {

		private Turnover turnover;

		public ChangeTableTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Boolean doInBackground(Turnover... params) {
			final String url = activity.getString(R.string.base_uri) + "/changeTable";
			turnover = params[0];
			HttpEntity<Turnover> requestEntity = new HttpEntity<Turnover>(turnover, requestHeaders);
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Boolean.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Boolean result) {
			DodoroContext.getInstance().setTurnover(turnover);

			Intent intent = new Intent();
			intent.setClass(activity, MainActivity.class);
			activity.startActivity(intent);
		}
	}
}
