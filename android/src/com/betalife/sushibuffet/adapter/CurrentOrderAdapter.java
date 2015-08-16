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
		resourceId = R.layout.adapter_current_order;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		Order viewModel = getItem(position);
		if (viewModel == null) {
			return convertView;
		}

		String imageUrl = DodoroContext.getProductThumbUrl(viewModel.getProduct().getThumb());
		ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

		TextView name = (TextView) convertView.findViewById(R.id.name);
		name.setText(viewModel.getProduct().getProductName());

		OrderCountView orderCountView = new OrderCountView(viewModel.getProduct(), convertView, activity);
		orderCountView.init();

		// TextView count = (TextView) convertView.findViewById(R.id.count);
		// int productCount = result.getCount();
		// count.setText(activity.getString(R.string.lbl_count) + productCount);
		//
		// TextView price = (TextView) convertView.findViewById(R.id.price);
		// int productPrice = result.getProduct().getProductPrice();
		// price.setText(activity.getString(R.string.lbl_price) +
		// DodoroContext.getDisplayPrice(productPrice)
		// + getActivity().getString(R.string.lbl_eur));
		//
		// TextView totalPrice = (TextView)
		// convertView.findViewById(R.id.totalPrice);
		// int total = productCount * productPrice;
		//
		// totalPrice.setText(activity.getString(R.string.lbl_subtotal) +
		// DodoroContext.getDisplayPrice(total)
		// + getActivity().getString(R.string.lbl_eur));

		return convertView;
	}

}
