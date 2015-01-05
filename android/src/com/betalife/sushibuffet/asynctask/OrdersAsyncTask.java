package com.betalife.sushibuffet.asynctask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.util.SparseArray;
import android.widget.ListView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.OrderAdapter;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class OrdersAsyncTask extends AbstractAsyncTask<Void, List<Order>> {

	public OrdersAsyncTask(Activity activity) {
		this(activity, false);
	}

	public OrdersAsyncTask(Activity activity, boolean showProgressDialog) {
		super(activity, showProgressDialog);
	}

	@Override
	public void postCallback(List<Order> result) {
		SparseArray<Order> map = new SparseArray<Order>();
		for (Order order : result) {
			int key = order.getProduct().getId();
			Order value = map.get(key);
			if (value == null) {
				Order copy = order.copy();
				map.put(key, copy);
			} else {
				value.setCount(value.getCount() + order.getCount());
			}
		}
		List<Order> list = new ArrayList<Order>();
		for (int i = 0; i < map.size(); i++) {
			list.add(map.valueAt(i));
		}
		OrderAdapter oa = new OrderAdapter(activity, list);
		ListView orders = (ListView) activity.findViewById(R.id.orders);
		orders.setAdapter(oa);

		int total = 0;
		for (Order order : list) {
			int productPrice = order.getProduct().getProductPrice();
			int productCount = order.getCount();
			total += productCount * productPrice;
		}

		TextView totalPrice = (TextView) activity.findViewById(R.id.totalPrice);
		totalPrice.setText("total: " + DodoroContext.getDisplayPrice(total) + " €");
	}

	@Override
	protected List<Order> inBackground(Void... params) {
		String url = activity.getString(R.string.base_uri) + "/orders/"
				+ DodoroContext.languageCode(activity) + "/"
				+ DodoroContext.getInstance().getTurnover().getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Order[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Order[].class);
		List<Order> orders = Arrays.asList(responseEntity.getBody());

		return orders;
	}
}