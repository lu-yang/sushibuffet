package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.model.Turnover;

public abstract class UpdateTurnoverTask extends AbstractAsyncTask<Turnover, BooleanExchange> {

	String url = base_url + "/turnover";

	public UpdateTurnoverTask(Activity activity) {
		super(activity);
	}

	@Override
	protected BooleanExchange inBackground(Turnover... params) {
		HttpEntity<Turnover> requestEntity = new HttpEntity<Turnover>(params[0], requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

}