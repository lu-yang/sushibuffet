package com.betalife.sushibuffet.activity;

import java.util.List;

import android.widget.TextView;

import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class BaseFragmentHistory extends BaseFragment {

	protected AsyncTaskCallback<Order> callback;

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

		TextView discountTextView = (TextView) getActivity().findViewById(R.id.discount);
		String discountText = getActivity().getString(R.string.lbl_discount);
		Integer percent = DodoroContext.getInstance().getTurnover().getDiscount();
		if (percent == null) {
			discountText += "-";
		} else if (percent == 0) {
			discountText += getActivity().getString(R.string.label_discount_free);
		} else {
			discountText += "-" + percent + "%";
		}
		discountTextView.setText(discountText);

		TextView discountPrice = (TextView) getActivity().findViewById(R.id.discountPrice);
		discountPrice.setText(getActivity().getString(R.string.lbl_discount_price)
				+ DodoroContext.getDiscountPrice(total, percent) + getActivity().getString(R.string.lbl_eur));
	}
}
