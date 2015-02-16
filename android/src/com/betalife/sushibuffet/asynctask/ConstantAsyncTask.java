package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;

import com.betalife.sushibuffet.activity.ChooseTableActivity;
import com.betalife.sushibuffet.exchange.ConstantExchange;
import com.betalife.sushibuffet.util.DodoroContext;

public class ConstantAsyncTask extends AbstractAsyncTask<Void, ConstantExchange> {

	public ConstantAsyncTask(Activity activity) {
		super(activity, false);
	}

	@Override
	public void postCallback(ConstantExchange result) {
		DodoroContext.getInstance().setConstant(result.getModel());
		Intent intent = new Intent();
		intent.setClass(activity, ChooseTableActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected ConstantExchange inBackground(Void... params) {
		String url = base_url + "/constant";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		ResponseEntity<ConstantExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, ConstantExchange.class);
		return responseEntity.getBody();
	}
}