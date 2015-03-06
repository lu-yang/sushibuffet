package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.exchange.TurnoverExchange;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class OpenTableTask extends AbstractAsyncTask<Turnover, TurnoverExchange> {

	String url = base_url + "/openTable";

	public OpenTableTask(Activity activity) {
		super(activity);
	}

	@Override
	protected TurnoverExchange inBackground(Turnover... params) {
		HttpEntity<Turnover> requestEntity = new HttpEntity<Turnover>(params[0], requestHeaders);
		ResponseEntity<TurnoverExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, TurnoverExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(TurnoverExchange result) {
		DodoroContext.goToMainActivity(result.getModel(), activity);
	}

}