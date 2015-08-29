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
	private TextView roundOrderCount;

	public OrderCountView(Product viewModel, View convertView, Activity activity) {
		this.viewModel = viewModel;
		this.convertView = convertView;
		this.activity = activity;
	}

	public void init() {
		roundOrderCount = (TextView) activity.findViewById(R.id.roundOrderCount);

		final DodoroContext instance = DodoroContext.getInstance();

		final TextView num = (TextView) convertView.findViewById(R.id.num);
		int count = getCount(viewModel);
		setTextAndTag(num, count);

		Button add = (Button) convertView.findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer oldValue = (Integer) num.getTag();
				int newValue = oldValue + 1;
				setTextAndTag(num, newValue);
				changeCount(viewModel, newValue);
				instance.fillRoundOrderCount(activity.getResources(), roundOrderCount);
			}
		});

		Button subtract = (Button) convertView.findViewById(R.id.subtract);
		subtract.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Integer oldValue = (Integer) num.getTag();
				int newValue = oldValue - 1;
				if (newValue < 0) {
					newValue = 0;
				}
				setTextAndTag(num, newValue);
				changeCount(viewModel, newValue);
				instance.fillRoundOrderCount(activity.getResources(), roundOrderCount);
			}
		});
	}

	private void setTextAndTag(final TextView num, int count) {
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

	private void changeCount(Product result, int count) {
		changeOrderCount(result, count);
		changeCategoryCount();
	}

	private void changeCategoryCount() {
		ListView categories = (ListView) activity.findViewById(R.id.categories);
		CategoryAdapter adapter = (CategoryAdapter) categories.getAdapter();
		adapter.notifyDataSetChanged();
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
