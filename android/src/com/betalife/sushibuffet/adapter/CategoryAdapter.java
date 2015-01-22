package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class CategoryAdapter extends AAdapter<Category> {

	public CategoryAdapter(Activity activity, List<Category> categories) {
		super(activity, categories);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.adapter_category, parent, false);
		}

		Category result = getItem(position);
		if (result != null) {
			String imageUrl = DodoroContext.getCategoryThumbUrl(result.getThumb());
			ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(result.getName());
		}

		return convertView;
	}
}
