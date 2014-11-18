package com.betalife.sushibuffet.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class OrderDialog extends Dialog {
	private Product product;
	private Activity parent;

	public OrderDialog(Activity parent, Product result) {
		super(parent, R.style.OrderDialog);
		this.parent = parent;
		this.product = result;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.order_dialog);

		final NumberPicker num = (NumberPicker) findViewById(R.id.num);
		num.setMaxValue(20);
		num.setMinValue(0);
		num.setValue(getCount(product, num));

		Button ok = (Button) findViewById(R.id.order_dialog_ok);
		ok.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NumberPicker num = (NumberPicker) findViewById(R.id.num);
				changeCount(product, num);
				dismiss();
			}
		});

		Button cancel = (Button) findViewById(R.id.order_dialog_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		String imageUrl = DodoroContext.getProductThumbUrl(product.getThumb());
		ImageViewUtil.setImage(imageUrl, (ImageView) findViewById(R.id.thumb));

		TextView name = (TextView) findViewById(R.id.name);
		name.setText(product.getProductName());

		TextView desc = (TextView) findViewById(R.id.desc);
		desc.setText(product.getDescription());

		TextView price = (TextView) findViewById(R.id.price);
		price.setText("" + product.getProductPrice());

	}

	private int getCount(Product result, NumberPicker num) {
		ListView orders = (ListView) parent.findViewById(R.id.current_orders);
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

	private void changeCount(Product result, NumberPicker num) {

		changeOrderCount(result, num);

		changeCategoryCount();

	}

	private void changeCategoryCount() {
		ListView categories = (ListView) parent.findViewById(R.id.categories);
		CategoryAdapter adapter = (CategoryAdapter) categories.getAdapter();
		adapter.notifyDataSetChanged();
	}

	private void changeOrderCount(Product result, NumberPicker num) {
		ListView currentOrders = (ListView) parent.findViewById(R.id.current_orders);
		CurrentOrderAdapter adapter = (CurrentOrderAdapter) currentOrders.getAdapter();
		List<Order> list = adapter.getList();
		int index = -1;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct().getId() == result.getId()) {
				index = i;
				break;
			}
		}
		int count = num.getValue();
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

}
