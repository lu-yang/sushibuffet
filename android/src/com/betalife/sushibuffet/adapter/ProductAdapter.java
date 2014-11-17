package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.dialog.OrderDialog;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.AsyncImageLoader;
import com.betalife.sushibuffet.util.AsyncImageLoader.ImageCallback;
import com.betalife.sushibuffet.util.DodoroContext;

public class ProductAdapter extends AAdapter<Product> {

	public ProductAdapter(Activity activity, List<Product> products) {
		super(activity, products);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.product, parent, false);
		}

		final Product result = getItem(position);
		if (result != null) {
			final ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			String imageUrl = DodoroContext.getProductThumbUrl(result.getThumb());
			AsyncImageLoader asyncImageLoader = AsyncImageLoader.getInstance();
			asyncImageLoader.loadDrawable(imageUrl, new ImageCallback() {
				@Override
				public void imageLoaded(Drawable imageDrawable, String imageUrl) {
					thumb.setImageDrawable(imageDrawable);
				}
			});

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(result.getProductName());

			TextView desc = (TextView) convertView.findViewById(R.id.desc);
			desc.setText(result.getDescription());

			TextView price = (TextView) convertView.findViewById(R.id.price);
			price.setText("" + result.getProductPrice());

			thumb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Dialog dialog = new OrderDialog(activity, result);
					dialog.show();
				}

			});

		}

		return convertView;
	}

}
