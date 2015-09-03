package com.betalife.sushibuffet.activity;

import android.app.Activity;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.util.DodoroContext;

public class OrderCountRefresh implements Callback {

	private TextView[] roundOrderCounts;
	private Activity activity;

	public OrderCountRefresh(Activity activity, TextView... roundOrderCounts) {
		this.activity = activity;
		this.roundOrderCounts = roundOrderCounts;
	}

	@Override
	public void callback() {
		DodoroContext.getInstance().fillRoundOrderCount(activity.getResources(), roundOrderCounts);
		GridView products = (GridView) activity.findViewById(R.id.products);
		ProductAdapter productAdapter = (ProductAdapter) products.getAdapter();
		productAdapter.notifyDataSetChanged();

		ListView categories = (ListView) activity.findViewById(R.id.categories);
		CategoryAdapter adapter = (CategoryAdapter) categories.getAdapter();
		adapter.notifyDataSetChanged();
	}

}
