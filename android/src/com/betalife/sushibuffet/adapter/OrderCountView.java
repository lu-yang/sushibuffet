package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

public class OrderCountView {
	private Product viewModel;
	private View convertView;
	private Activity activity;

	public OrderCountView(Product viewModel, View convertView, Activity activity) {
		this.viewModel = viewModel;
		this.convertView = convertView;
		this.activity = activity;
	}

	public void init() {
		final TextView num = (TextView) convertView.findViewById(R.id.num);
		int count = getCount(viewModel);
		setCount(num, count);

		Button add = (Button) convertView.findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = (Integer) num.getTag() + 1;
				setCount(num, count);
				changeCount(viewModel, num);
			}
		});

		Button subtract = (Button) convertView.findViewById(R.id.subtract);
		subtract.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = (Integer) num.getTag() - 1;
				if (count < 0) {
					count = 0;
				}
				setCount(num, count);
				changeCount(viewModel, num);
			}
		});
	}

	private void setCount(final TextView num, int count) {
		num.setText("" + count);
		num.setTag(count);
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

	private void changeCount(Product result, TextView num) {
		changeOrderCount(result, num);
		changeCategoryCount();
	}

	private void changeCategoryCount() {
		ListView categories = (ListView) activity.findViewById(R.id.categories);
		CategoryAdapter adapter = (CategoryAdapter) categories.getAdapter();
		adapter.notifyDataSetChanged();
	}

	private void changeOrderCount(Product result, TextView num) {
		List<Order> list = DodoroContext.getInstance().getCurrentOrdersCache();
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct().getId() == result.getId()) {
				index = i;
				break;
			}
		}
		int count = (Integer) num.getTag();
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
