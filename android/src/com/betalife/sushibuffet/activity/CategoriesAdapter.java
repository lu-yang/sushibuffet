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

import com.betalife.sushibuffet.model.Categories;
import com.betalife.sushibuffet.util.AsyncImageLoader;
import com.betalife.sushibuffet.util.AsyncImageLoader.ImageCallback;

public class CategoriesAdapter extends BaseAdapter {
	private List<Categories> categories;
	private final LayoutInflater layoutInflater;

	public CategoriesAdapter(Context context, List<Categories> categories) {
		super();
		this.categories = categories;
		this.layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return categories.size();
	}

	@Override
	public Categories getItem(int position) {
		return categories.get(position);
	}

	@Override
	public long getItemId(int position) {
		return categories.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.category, parent, false);
		}

		Categories result = getItem(position);
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
			name.setText(result.getName());

			TextView desc = (TextView) convertView.findViewById(R.id.desc);
			desc.setText(result.getDescription());
		}

		return convertView;
	}

}
