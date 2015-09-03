package com.betalife.sushibuffet.adapter;

import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.Callback;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;
import com.betalife.sushibuffet.util.ImageViewUtil;

public class ProductAdapter extends AAdapter<Product> {
	private Callback callback;

	public ProductAdapter(Activity activity, List<Product> products, Callback callback) {
		super(activity, products);
		this.callback = callback;
		resourceId = R.layout.adapter_product;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		final Product viewModel = getItem(position);
		if (viewModel != null) {
			String imageUrl = DodoroContext.getProductThumbUrl(viewModel.getThumb());
			ImageView thumb = (ImageView) convertView.findViewById(R.id.thumb);
			ImageViewUtil.setImage(imageUrl, thumb);

			TextView name = (TextView) convertView.findViewById(R.id.name);
			name.setText(viewModel.getProductName());

			OrderCountView orderCountView = new OrderCountView(viewModel, convertView, callback);
			orderCountView.init();

			// final TextView num = (TextView)
			// convertView.findViewById(R.id.num);
			// int count = getCount(viewModel);
			// setCount(num, count);
			//
			// Button add = (Button) convertView.findViewById(R.id.add);
			// add.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// int count = (Integer) num.getTag() + 1;
			// setCount(num, count);
			// changeCount(viewModel, num);
			// }
			// });
			//
			// Button subtract = (Button)
			// convertView.findViewById(R.id.subtract);
			// subtract.setOnClickListener(new View.OnClickListener() {
			//
			// @Override
			// public void onClick(View v) {
			// int count = (Integer) num.getTag() - 1;
			// if (count < 0) {
			// count = 0;
			// }
			// setCount(num, count);
			// changeCount(viewModel, num);
			// }
			// });

			// price
			// TextView price = (TextView) convertView.findViewById(R.id.price);
			// price.setText("" +
			// DodoroContext.getDisplayPrice(result.getProductPrice())
			// + getActivity().getString(R.string.lbl_eur) +
			// DodoroContext.getNum(result.getNum()));

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

	// private void setCount(final TextView num, int count) {
	// num.setText("" + count);
	// num.setTag(count);
	// }
	//
	// private int getCount(Product result) {
	// List<Order> list = DodoroContext.getInstance().getCurrentOrdersCache();
	// int count = 0;
	// for (int i = 0; i < list.size(); i++) {
	// Order order = list.get(i);
	// if (order.getProduct().getId() == result.getId()) {
	// count = order.getCount();
	// break;
	// }
	// }
	// return count;
	// }
	//
	// private void changeCount(Product result, TextView num) {
	// changeOrderCount(result, num);
	// changeCategoryCount();
	// }
	//
	// private void changeCategoryCount() {
	// ListView categories = (ListView) activity.findViewById(R.id.categories);
	// CategoryAdapter adapter = (CategoryAdapter) categories.getAdapter();
	// adapter.notifyDataSetChanged();
	// }
	//
	// private void changeOrderCount(Product result, TextView num) {
	// List<Order> list = DodoroContext.getInstance().getCurrentOrdersCache();
	// int index = -1;
	// for (int i = 0; i < list.size(); i++) {
	// if (list.get(i).getProduct().getId() == result.getId()) {
	// index = i;
	// break;
	// }
	// }
	// int count = (Integer) num.getTag();
	// if (index == -1 && count != 0) {
	// Order o = new Order();
	// o.setCount(count);
	// o.setProduct(result);
	// o.setTurnover(DodoroContext.getInstance().getTurnover());
	// list.add(o);
	// } else if (index != -1 && count == 0) {
	// list.remove(index);
	// } else if (index != -1 && count != 0) {
	// list.get(index).setCount(count);
	// }
	// }
}
