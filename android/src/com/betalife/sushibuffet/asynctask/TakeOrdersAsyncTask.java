package com.betalife.sushibuffet.asynctask;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class TakeOrdersAsyncTask extends AbstractAsyncTask<Void, BooleanExchange> {

	public TakeOrdersAsyncTask(Activity activity) {
		super(activity, true);
	}

	@Override
	protected BooleanExchange inBackground(Void... params) {
		String url = base_url + "/orders";

		List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		HttpEntity<List<Order>> requestEntity = new HttpEntity<List<Order>>(currentOrdersCache,
				requestHeaders);
		ResponseEntity<BooleanExchange> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
				requestEntity, BooleanExchange.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(BooleanExchange result) {
		if (result.getModel() != null && result.getModel()) {
			DodoroContext.getInstance().getCurrentOrdersCache().clear();

			MainActivity mainActivity = (MainActivity) activity;
			mainActivity.changeTab(mainActivity.getTabIndex() + 1);
		} else {
			Toast.makeText(activity, activity.getString(R.string.err_take_orders), Toast.LENGTH_SHORT).show();
		}
	}

}
