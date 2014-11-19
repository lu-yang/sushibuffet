package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class CurrentOrderAdapter extends AAdapter<Order> {

	public CurrentOrderAdapter(Activity activity, List<Order> orders) {
		super(activity, orders);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.current_order, parent, false);
		}

		Order result = getItem(position);
		if (result == null) {
			return convertView;
		}

		String imageUrl = DodoroContext.getProductThumbUrl(result.getProduct().getThumb());
		ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(result.getProduct().getProductName());

		TextView count = (TextView) convertView.findViewById(R.id.count);
		int productCount = result.getCount();
		count.setText("" + productCount);

		TextView price = (TextView) convertView.findViewById(R.id.price);
		float productPrice = result.getProduct().getProductPrice();
		price.setText("" + productPrice);

		TextView totalPrice = (TextView) convertView.findViewById(R.id.totalPrice);
		float total = 0;
		for (int i = 0; i < productCount; i++) {
			total += productPrice;
		}
		totalPrice.setText("" + total);

		return convertView;
	}

}