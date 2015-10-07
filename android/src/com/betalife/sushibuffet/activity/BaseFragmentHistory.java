package com.betalife.sushibuffet.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;

public class BaseFragmentHistory extends BaseFragment {

	protected AsyncTaskCallback<Order> callback;
	protected View orders;

	// private TotalPriceView totalPriceView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		orders = view.findViewById(R.id.orders);
		// totalPriceView = new TotalPriceView(view);
		return view;
	}

	@Override
	public void show() {
		OrdersAsyncTask task = new OrdersAsyncTask(getActivity(), true, callback);
		task.execute();
	}

	protected void setTotalPrice(List<Order> list) {
		// int total = 0;
		// for (Order order : list) {
		// int productPrice = order.getProduct().getProductPrice();
		// int productCount = order.getCount();
		// total += productCount * productPrice;
		// }
		// Integer discount =
		// DodoroContext.getInstance().getTurnover().getDiscount();
		// totalPriceView.setTotalPrice(total, discount);
	}
}
