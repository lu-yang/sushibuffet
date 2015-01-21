package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class OrdersAsyncTask extends AbstractAsyncTask<Void, List<Order>> {

	private AsyncTaskCallback<Order> callback;

	public OrdersAsyncTask(Activity activity, boolean showProgressDialog, AsyncTaskCallback<Order> callback) {
		super(activity, showProgressDialog);
		this.callback = callback;
	}

	@Override
	public void postCallback(List<Order> result) {
		callback.callback(result);
	}

	@Override
	protected List<Order> inBackground(Void... params) {
		String url = base_url + "/orders/" + DodoroContext.languageCode(activity) + "/"
				+ DodoroContext.getInstance().getTurnover().getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Order[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Order[].class);
		List<Order> orders = Arrays.asList(responseEntity.getBody());

		return orders;
	}
}