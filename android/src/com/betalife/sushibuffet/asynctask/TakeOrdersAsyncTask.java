package com.betalife.sushibuffet.asynctask;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.exchange.TurnoverExchange;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class TakeOrdersAsyncTask extends AbstractAsyncTask<Void, TurnoverExchange> {

	public TakeOrdersAsyncTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected TurnoverExchange inBackground(Void... params) {
		String url = base_url + "/orders";

		List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		HttpEntity<List<Order>> requestEntity = new HttpEntity<List<Order>>(currentOrdersCache,
				requestHeaders);
		ResponseEntity<TurnoverExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, TurnoverExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(TurnoverExchange result) {
		DodoroContext instance = DodoroContext.getInstance();
		instance.getCurrentOrdersCache().clear();
		instance.setTurnover(result.getModel());

		MainActivity mainActivity = (MainActivity) activity;
		mainActivity.changeTab(mainActivity.getTabIndex() + 1);
	}

}
