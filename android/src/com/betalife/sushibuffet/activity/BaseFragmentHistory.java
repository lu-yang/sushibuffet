package com.betalife.sushibuffet.activity;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class BaseFragmentHistory extends Fragment implements Refreshable {

	protected int layout;

	protected AsyncTaskCallback<Order> callback;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layout, container, false);
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

	protected void setTotalPrice(List<Order> list) {
		int total = 0;
		for (Order order : list) {
			int productPrice = order.getProduct().getProductPrice();
			int productCount = order.getCount();
			total += productCount * productPrice;
		}

		TextView totalPrice = (TextView) getActivity().findViewById(R.id.totalPrice);
		totalPrice.setText(getActivity().getString(R.string.lbl_total) + DodoroContext.getDisplayPrice(total)
				+ getActivity().getString(R.string.lbl_eur));

		TextView discount = (TextView) getActivity().findViewById(R.id.discount);
		String discountText = getActivity().getString(R.string.lbl_discount);
		int percent = DodoroContext.getInstance().getTurnover().getDiscount();
		if (percent == 100) {
			discountText += "-";
		} else if (percent == 0) {
			discountText += getActivity().getString(R.string.label_discount_free);
		} else {
			discountText += percent + "%";
		}
		discount.setText(discountText);

		TextView discountPrice = (TextView) getActivity().findViewById(R.id.discountPrice);
		discountPrice
				.setText(getActivity().getString(R.string.lbl_discount_price)
						+ DodoroContext.getDiscountPrice(total * percent)
						+ getActivity().getString(R.string.lbl_eur));
	}
}
