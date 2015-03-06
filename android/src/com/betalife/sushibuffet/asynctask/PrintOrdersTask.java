package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.util.DodoroContext;

public class PrintOrdersTask extends AbstractAsyncTask<Void, BooleanExchange> {

	public PrintOrdersTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected BooleanExchange inBackground(Void... params) {
		String url = base_url + "/printOrders/" + DodoroContext.languageCode(activity) + "/"
				+ DodoroContext.getInstance().getTurnover().getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(BooleanExchange result) {
		if (result.getModel() != null && result.getModel()) {
			Toast.makeText(activity, activity.getString(R.string.setting_activity_printOrders_mes),
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(activity, activity.getString(R.string.setting_activity_printOrders_err_mes),
					Toast.LENGTH_SHORT).show();
		}
	}
}