package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.Callback;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

public class OrderCountView {
	private Product viewModel;
	private View convertView;
	private Callback refresh;

	public OrderCountView(Product viewModel, View convertView, Callback refresh) {
		this.viewModel = viewModel;
		this.convertView = convertView;
		this.refresh = refresh;
	}

	public void init() {

		final TextView num = (TextView) convertView.findViewById(R.id.num);
		int count = getCount(viewModel);
		setTextAndTag(num, count);

		TextView add = (TextView) convertView.findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer oldValue = (Integer) num.getTag();
				int newValue = oldValue + 1;
				refresh(num, newValue);
			}

		});

		TextView subtract = (TextView) convertView.findViewById(R.id.subtract);
		subtract.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer oldValue = (Integer) num.getTag();
				int newValue = oldValue - 1;
				if (newValue < 0) {
					newValue = 0;
				}
				refresh(num, newValue);
			}
		});
	}

	private void setTextAndTag(TextView num, int count) {
		num.setText("" + count);
		num.setTag(count);
	}

	private void refresh(TextView num, int newValue) {
		setTextAndTag(num, newValue);
		changeOrderCount(viewModel, newValue);
		refresh.callback();
	}

	private int getCount(Product result) {
		List<Order> list = DodoroContext.getInstance().getCurrentOrdersCache();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {
			Order order = list.get(i);
			if (order.getProduct().getId() == result.getId()) {
				count = order.getCount();
				break;
			}
		}
		return count;
	}

	private void changeOrderCount(Product result, int count) {
		List<Order> list = DodoroContext.getInstance().getCurrentOrdersCache();
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct().getId() == result.getId()) {
				index = i;
				break;
			}
		}
		if (index == -1 && count != 0) {
			Order o = new Order();
			o.setCount(count);
			o.setProduct(result);
			o.setTurnover(DodoroContext.getInstance().getTurnover());
			list.add(o);
		} else if (index != -1 && count == 0) {
			list.remove(index);
		} else if (index != -1 && count != 0) {
			list.get(index).setCount(count);
		}
	}
}
