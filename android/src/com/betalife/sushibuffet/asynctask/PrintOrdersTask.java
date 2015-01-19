package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.util.DodoroContext;

public class PrintOrdersTask extends AbstractAsyncTask<Void, Boolean> {

	public PrintOrdersTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected Boolean inBackground(Void... params) {
		final String url = base_url + "/printOrders/" + DodoroContext.languageCode(activity) + "/"
				+ DodoroContext.getInstance().getTurnover().getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Boolean.class);
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