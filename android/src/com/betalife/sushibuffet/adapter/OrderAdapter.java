package com.betalife.sushibuffet.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class OrderAdapter extends AAdapter<Order> {

	public OrderAdapter(Activity activity) {
		super(activity);
		resourceId = R.layout.adapter_order;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		Order result = getItem(position);
		if (result != null) {
			String imageUrl = DodoroContext.getProductThumbUrl(result.getProduct().getThumb());
			ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(result.getProduct().getProductName());

			TextView count = (TextView) convertView.findViewById(R.id.count);
			int productCount = result.getCount();
			count.setText(activity.getString(R.string.lbl_count) + productCount);

			TextView price = (TextView) convertView.findViewById(R.id.price);
			int productPrice = result.getProduct().getProductPrice();
			price.setText(activity.getString(R.string.lbl_price)
					+ DodoroContext.getDisplayPrice(productPrice) + getActivity().getString(R.string.lbl_eur));

			TextView totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
			int subTotal = productCount * productPrice;
			totalPrice.setText(activity.getString(R.string.lbl_subtotal)
					+ DodoroContext.getDisplayPrice(subTotal) + getActivity().getString(R.string.lbl_eur));

		}

		return convertView;
	}

	public void setRawList(List<Order> list) {
		SparseArray<Order> map = new SparseArray<Order>();
		for (Order order : list) {
			int key = order.getProduct().getId();
			Order value = map.get(key);
			if (value == null) {
				Order copy = order.copy();
				map.put(key, copy);
			} else {
				value.setCount(value.getCount() + order.getCount());
			}
		}
		List<Order> squeeze = new ArrayList<Order>();
		for (int i = 0; i < map.size(); i++) {
			squeeze.add(map.valueAt(i));
		}

		super.setList(squeeze);
	}

}
