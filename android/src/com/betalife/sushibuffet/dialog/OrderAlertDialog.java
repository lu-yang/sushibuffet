package com.betalife.sushibuffet.dialog;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class OrderAlertDialog {

	private Activity activity;
	private AlertDialog.Builder builder;

	public OrderAlertDialog(Activity activity, final Product product) {
		this.activity = activity;

		LayoutInflater layoutInflater = activity.getLayoutInflater();
		View layout = layoutInflater.inflate(R.layout.dialog_order, null);

		final TextView num = (TextView) layout.findViewById(R.id.num);
		int count = getCount(product);
		setCount(num, count);

		Button add = (Button) layout.findViewById(R.id.add);
		add.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = (Integer) num.getTag() + 1;
				setCount(num, count);
				changeCount(product, num);
			}
		});

		Button subtract = (Button) layout.findViewById(R.id.subtract);
		subtract.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = (Integer) num.getTag() - 1;
				if (count < 0) {
					count = 0;
				}
				setCount(num, count);
				changeCount(product, num);
			}
		});

		String imageUrl = DodoroContext.getProductThumbUrl(product.getThumb());
		ImageViewUtil.setImage(imageUrl, (ImageView) layout.findViewById(R.id.thumb));

		TextView name = (TextView) layout.findViewById(R.id.name);
		name.setText(product.getProductName());

		TextView desc = (TextView) layout.findViewById(R.id.desc);
		desc.setText(activity.getString(R.string.lbl_desc) + product.getDescription());

		TextView price = (TextView) layout.findViewById(R.id.price);
		price.setText("" + DodoroContext.getDisplayPrice(product.getProductPrice())
				+ activity.getString(R.string.lbl_eur) + DodoroContext.getNum(product.getNum()));

		builder = new AlertDialog.Builder(activity);
		builder.setView(layout);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				changeCount(product, num);
			}
		});

		builder.setCancelable(true);

	}

	private void setCount(final TextView num, int count) {
		num.setText("" + count);
		num.setTag(count);
	}

	private int getCount(Product result) {
		ListView orders = (ListView) activity.findViewById(R.id.current_orders);
		CurrentOrderAdapter adapter = (CurrentOrderAdapter) orders.getAdapter();
		List<Order> list = adapter.getList();
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
		ListView currentOrders = (ListView) activity.findViewById(R.id.current_orders);
		CurrentOrderAdapter adapter = (CurrentOrderAdapter) currentOrders.getAdapter();
		List<Order> list = adapter.getList();
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
		adapter.notifyDataSetChanged();
	}

	public void show() {
		builder.create().show();
	}

}
