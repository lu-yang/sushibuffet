package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class CheckoutTask extends AbstractAsyncTask<Turnover, BooleanExchange> {

	public CheckoutTask(Activity activity) {
		super(activity);
	}

	@Override
	protected BooleanExchange inBackground(Turnover... params) {
		Turnover turnover = params[0];
		final String url = base_url + "/checkout/" + turnover.getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(BooleanExchange result) {
		if (result.getModel() != null && result.getModel()) {
			Toast.makeText(activity, activity.getString(R.string.setting_activity_checkout_ok),
					Toast.LENGTH_SHORT).show();
			DodoroContext.getInstance().setTurnover(null);

			// restart app
			DodoroContext.restartApp(activity);

		} else {
			Toast.makeText(activity, activity.getString(R.string.setting_activity_checkout_err),
					Toast.LENGTH_SHORT).show();
		}
	}
}