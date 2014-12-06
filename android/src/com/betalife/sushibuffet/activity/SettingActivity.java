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
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.ChangeTableAdapter;
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
			Turnover turnover = params[0];
			final String url = activity.getString(R.string.base_uri) + "/checkout/" + turnover.getId();
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
		Intent intent = new Intent();
		intent.putExtra("adapter", new ChangeTableAdapter());
		intent.setClass(this, TableActivity.class);
		startActivity(intent);
	}

	public void printOrders(View view) {
		PrintOrdersTask task = new PrintOrdersTask(this);
		task.execute();
	}

	private class PrintOrdersTask extends AbstractAsyncTask<Void, Boolean> {

		public PrintOrdersTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			final String url = activity.getString(R.string.base_uri) + "/printOrders/"
					+ DodoroContext.languageCode(activity) + "/"
					+ DodoroContext.getInstance().getTurnover().getId();
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Boolean.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Boolean result) {
			if (result) {
				Toast.makeText(activity, activity.getString(R.string.setting_activity_printOrders_mes),
						Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(activity, activity.getString(R.string.setting_activity_printOrders_err_mes),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
