package com.betalife.sushibuffet.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class BaseFragmentHistory extends BaseFragment {

	protected AsyncTaskCallback<Order> callback;
	private TextView discountTextView;
	private TextView totalPriceTextView;
	private String LBL_TOTAL;
	private String LBL_EUR;
	private String LBL_DISCOUNT;
	private String LBL_DISCOUNT_NO;
	private String LBL_DISCOUNT_FREE;
	private String LBL_DISCOUNT_PRICE;
	private TextView discountPriceTextView;
	protected View orders;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		LBL_TOTAL = getString(R.string.lbl_total);
		LBL_EUR = getString(R.string.lbl_eur);
		LBL_DISCOUNT = getString(R.string.lbl_discount);
		LBL_DISCOUNT_NO = getString(R.string.lbl_discount_no);
		LBL_DISCOUNT_FREE = getString(R.string.lbl_discount_free);
		LBL_DISCOUNT_PRICE = getString(R.string.lbl_discount_price);
		totalPriceTextView = (TextView) view.findViewById(R.id.totalPrice);
		discountTextView = (TextView) view.findViewById(R.id.discount);
		discountPriceTextView = (TextView) view.findViewById(R.id.discountPrice);
		orders = view.findViewById(R.id.orders);
		return view;
	}

	@Override
	public void refresh() {
		super.refresh();
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

		totalPriceTextView.setText(LBL_TOTAL + DodoroContext.getDisplayPrice(total) + LBL_EUR);

		String discountText = null;
		Integer percent = DodoroContext.getInstance().getTurnover().getDiscount();
		if (percent == null) {
			discountText = LBL_DISCOUNT_NO;
		} else if (percent == 0) {
			discountText = LBL_DISCOUNT_FREE;
		} else {
			discountText = "-" + percent + "%";
		}
		discountTextView.setText(LBL_DISCOUNT + discountText);

		discountPriceTextView.setText(LBL_DISCOUNT_PRICE + DodoroContext.getDiscountPrice(total, percent)
				+ LBL_EUR);
	}
}
