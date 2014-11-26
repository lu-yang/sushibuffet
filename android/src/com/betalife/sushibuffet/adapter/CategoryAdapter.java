package com.betalife.sushibuffet.adapter;

import java.util.List;

import org.springframework.util.CollectionUtils;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class CategoryAdapter extends AAdapter<Category> {

	public CategoryAdapter(Activity activity, List<Category> categories) {
		super(activity, categories);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.category, parent, false);
		}

		Category result = getItem(position);
		if (result != null) {
			String imageUrl = DodoroContext.getCategoryThumbUrl(result.getThumb());
			ImageViewUtil.setImage(imageUrl, (ImageView) convertView.findViewById(R.id.thumb));

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(result.getName());

			//TextView desc = (TextView) convertView.findViewById(R.id.desc);
			//desc.setText(result.getDescription());

			List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
			if (!CollectionUtils.isEmpty(currentOrdersCache)) {
				int count = 0;

				for (Order order : currentOrdersCache) {
					Product product = order.getProduct();
					if (product.getCategoryId() == result.getId()) {
						count += order.getCount();
					}
				}

				TextView current_order_count = (TextView) convertView.findViewById(R.id.current_order_count);
				current_order_count.setText(count == 0 ? "" : "" + count);
			}

		}

		return convertView;
	}
}
