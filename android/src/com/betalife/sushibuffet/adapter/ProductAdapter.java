package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class ProductAdapter extends AAdapter<Product> {

	public ProductAdapter(Activity activity, List<Product> products) {
		super(activity, products);
		resourceId = R.layout.adapter_product;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		final Product result = getItem(position);
		if (result != null) {
			String imageUrl = DodoroContext.getProductThumbUrl(result.getThumb());
			ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			ImageViewUtil.setImage(imageUrl, thumb);

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(result.getProductName());

			TextView price = (TextView) convertView.findViewById(R.id.price);
			price.setText("" + DodoroContext.getDisplayPrice(result.getProductPrice())
					+ getActivity().getString(R.string.lbl_eur) + DodoroContext.getNum(result.getNum()));

			// thumb.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// Dialog dialog = new OrderDialog(activity, result);
			// dialog.show();
			// }
			//
			// });

		}

		return convertView;
	}
}
