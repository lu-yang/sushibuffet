package com.betalife.sushibuffet.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.DetailOrderAdapter;
import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class FragmentDetailHistory extends Fragment implements Refreshable {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_detail_history, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	@Override
	public void refresh() {
		OrdersAsyncTask task = new OrdersAsyncTask(getActivity(), true, callback);
		task.execute();
	}

	private void setTotalPrice(List<Order> list) {
		int total = 0;
		for (Order order : list) {
			int productPrice = order.getProduct().getProductPrice();
			int productCount = order.getCount();
			total += productCount * productPrice;
		}

		TextView totalPrice = (TextView) getActivity().findViewById(R.id.totalPrice);
		totalPrice.setText(getActivity().getString(R.string.lbl_total) + DodoroContext.getDisplayPrice(total)
				+ " €");
	}

	private AsyncTaskCallback<Order> callback = new AsyncTaskCallback<Order>() {
		@Override
		public void callback(List<Order> list) {
			setTotalPrice(list);

			DetailOrderAdapter adapter = new DetailOrderAdapter(getActivity());
			adapter.setRawList(list);
			ExpandableListView orders = (ExpandableListView) getActivity().findViewById(R.id.orders);
			orders.setAdapter(adapter);
			int groupCount = adapter.getGroupCount();
			for (int i = 0; i < groupCount; i++) {
				orders.expandGroup(i);
			}
		}

	};
}
