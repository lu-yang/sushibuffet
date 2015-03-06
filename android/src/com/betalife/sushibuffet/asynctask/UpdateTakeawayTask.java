package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.util.DodoroContext;

public class UpdateTakeawayTask extends AbstractAsyncTask<Takeaway, BooleanExchange> {

	private String url = base_url + "/takeaway/";

	public UpdateTakeawayTask(Activity activity, boolean checkout) {
		super(activity);
		url += checkout;
	}

	@Override
	protected BooleanExchange inBackground(Takeaway... params) {
		HttpEntity<Takeaway> requestEntity = new HttpEntity<Takeaway>(params[0], requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.PUT,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(BooleanExchange result) {
		Toast.makeText(activity, activity.getString(R.string.setting_activity_takeaway_ok),
				Toast.LENGTH_SHORT).show();
		DodoroContext.getInstance().setTakeaway(null);

		// restart app
		DodoroContext.restartApp(activity);
	}

}