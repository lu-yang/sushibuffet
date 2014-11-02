package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.OrderAdapter;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class FragmentHistory extends Fragment implements Refreshable {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	private class OrdersAsyncTask extends AbstractAsyncTask<Void, List<Order>> {

		public OrdersAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(List<Order> result) {
			OrderAdapter oa = new OrderAdapter(activity, result);
			ListView orders = (ListView) activity.findViewById(R.id.orders);
			orders.setAdapter(oa);
		}

		@Override
		protected List<Order> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/orders/"
					+ DodoroContext.languageCode(getActivity()) + "/"
					+ DodoroContext.getInstance().getTurnover().getId();
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Order[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Order[].class);
			List<Order> orders = Arrays.asList(responseEntity.getBody());

			return orders;
		}
	}

	@Override
	public void refresh() {
		OrdersAsyncTask task = new OrdersAsyncTask(getActivity());
		task.execute();
	}
}
