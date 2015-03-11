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

public class RemoveTakeawayTask extends AbstractAsyncTask<Takeaway, BooleanExchange> {

	private String url = base_url + "/takeaway";

	public RemoveTakeawayTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected BooleanExchange inBackground(Takeaway... params) {
		url += "/" + params[0].getId();
		HttpEntity<Void> requestEntity = new HttpEntity<Void>(requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(BooleanExchange result) {
		DodoroContext.getInstance().setTakeaway(null);
		// restart app
		DodoroContext.restartApp(activity);

		Toast.makeText(activity, activity.getString(R.string.setting_activity_remove_takeaway_ok),
				Toast.LENGTH_SHORT).show();
	}

}