package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.AsyncImageLoader;
import com.betalife.sushibuffet.util.AsyncImageLoader.ImageCallback;
import com.betalife.sushibuffet.util.DodoroContext;

public class ProductAdapter extends AAdapter<Product> {

	private String productRootUrl;

	public ProductAdapter(Activity activity, List<Product> products) {
		super(activity, products);
		productRootUrl = DodoroContext.getInstance().getConstant().getProductRootUrl();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.product, parent, false);
		}

		final Product result = getItem(position);
		if (result != null) {
			final ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			String imageUrl = productRootUrl + result.getThumb();
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

			final NumberPicker num = (NumberPicker) convertView.findViewById(R.id.num);
			num.setMaxValue(20);
			num.setMinValue(0);
			num.setValue(0);
			num.setOnValueChangedListener(new OnValueChangeListener() {

				@Override
				public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
					num.setValue(newVal);
					changeCount(result, num);
				}
			});

			thumb.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					num.setValue(num.getValue() + 1);
					changeCount(result, num);
				}

			});

		}

		return convertView;
	}

	private void changeCount(Product result, NumberPicker num) {
		ListView orders = (ListView) activity.findViewById(R.id.list);
		OrderAdapter adapter = (OrderAdapter) orders.getAdapter();
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
