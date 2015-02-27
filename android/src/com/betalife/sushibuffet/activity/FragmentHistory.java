package com.betalife.sushibuffet.activity;

import java.util.List;

import android.widget.ListView;

import com.betalife.sushibuffet.adapter.OrderAdapter;
import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.model.Order;

public class FragmentHistory extends BaseFragmentHistory {

	public FragmentHistory() {
		layout = R.layout.fragment_history;

		callback = new AsyncTaskCallback<Order>() {
			@Override
			public void callback(List<Order> list) {
				setTotalPrice(list);

				OrderAdapter adapter = new OrderAdapter(getActivity());
				adapter.setRawList(list);
				ListView orders = (ListView) getActivity().findViewById(R.id.orders);
				orders.setAdapter(adapter);
			}

		};
	}

}
