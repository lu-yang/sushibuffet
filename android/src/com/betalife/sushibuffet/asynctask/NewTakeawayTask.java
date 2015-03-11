package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.exchange.TakeawayExchange;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.util.DodoroContext;

public class NewTakeawayTask extends AbstractAsyncTask<Takeaway, TakeawayExchange> {

	String url = base_url + "/takeaway";

	public NewTakeawayTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected TakeawayExchange inBackground(Takeaway... params) {
		HttpEntity<Takeaway> requestEntity = new HttpEntity<Takeaway>(params[0], requestHeaders);
		ResponseEntity<TakeawayExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, TakeawayExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(TakeawayExchange result) {
		Takeaway takeaway = result.getModel();
		DodoroContext.getInstance().setTakeaway(takeaway);
		DodoroContext.goToMainActivity(takeaway.getTurnover(), activity);
	}

}