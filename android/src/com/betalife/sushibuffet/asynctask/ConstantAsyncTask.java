package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.activity.OpenTableActivity;
import com.betalife.sushibuffet.exchange.ConstantExchange;
import com.betalife.sushibuffet.util.DodoroContext;

public class ConstantAsyncTask extends AbstractAsyncTask<Void, ConstantExchange> {

	String url = base_url + "/constant";

	public ConstantAsyncTask(Activity activity) {
		super(activity, false);
	}

	@Override
	public void postCallback(ConstantExchange result) {
		DodoroContext.getInstance().setConstant(result.getModel());
		DodoroContext.goTo(OpenTableActivity.class, activity);
	}

	@Override
	protected ConstantExchange inBackground(Void... params) {
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		ResponseEntity<ConstantExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, ConstantExchange.class);
		return responseEntity.getBody();
	}
}