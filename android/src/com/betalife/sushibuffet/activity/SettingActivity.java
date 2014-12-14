package com.betalife.sushibuffet.activity;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

		TextView table_no = (TextView) findViewById(R.id.table_no);
		int tableId = DodoroContext.getInstance().getTurnover().getTableId();
		table_no.setText(getString(R.string.lbl_table_no) + tableId);

	}

	public void checkout(View view) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(R.string.setting_activity_checkout);
		dialog.setMessage(getString(R.string.setting_activity_checkout_msg));
		final Activity _this = this;
		dialog.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				CheckoutTask task = new CheckoutTask(_this);
				task.execute(DodoroContext.getInstance().getTurnover());
			}
		});

		dialog.setNegativeButton(R.string._no, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		dialog.create().show();

	}

	private class CheckoutTask extends AbstractAsyncTask<Turnover, Boolean> {

		public CheckoutTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Boolean inBackground(Turnover... params) {
			Turnover turnover = params[0];
			final String url = activity.getString(R.string.base_uri) + "/checkout/" + turnover.getId();
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Boolean.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Boolean result) {
			if (result) {
				Toast.makeText(activity, activity.getString(R.string.setting_activity_checkout_ok),
						Toast.LENGTH_SHORT).show();
				DodoroContext.getInstance().setTurnover(null);

				Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				// Intent intent = new Intent();
				// intent.setClass(activity, AccessActivity.class);
				// activity.startActivity(intent);
			} else {
				Toast.makeText(activity, activity.getString(R.string.setting_activity_checkout_err),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void changeTable(View view) {
		Intent intent = new Intent();
		Bundle mBundle = new Bundle();
		mBundle.putSerializable("adapter", new ChangeTableAdapter());
		intent.putExtras(mBundle);
		intent.setClass(this, TableActivity.class);
		startActivity(intent);
	}

	public void printOrders(View view) {
		PrintOrdersTask task = new PrintOrdersTask(this);
		task.execute();
	}

	private class PrintOrdersTask extends AbstractAsyncTask<Void, Boolean> {

		public PrintOrdersTask(Activity activity) {
			super(activity, true);
		}

		@Override
		protected Boolean inBackground(Void... params) {
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
