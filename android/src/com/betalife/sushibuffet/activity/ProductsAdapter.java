package com.betalife.sushibuffet.activity;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.model.Products;
import com.betalife.sushibuffet.util.AsyncImageLoader;
import com.betalife.sushibuffet.util.AsyncImageLoader.ImageCallback;

public class ProductsAdapter extends BaseAdapter {
	private List<Products> products;
	private final LayoutInflater layoutInflater;

	public ProductsAdapter(Context context, List<Products> products) {
		super();
		this.products = products;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return products.size();
	}

	@Override
	public Products getItem(int position) {
		return products.get(position);
	}

	@Override
	public long getItemId(int position) {
		return products.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.product, parent, false);
		}

		Products result = getItem(position);
		if (result != null) {
			final ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			String imageUrl = "http://www.baidu.com/img/bd_logo1.png";
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
		}

		return convertView;
	}

}
