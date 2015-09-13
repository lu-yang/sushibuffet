package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.OrderCountRefresh;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.exchange.ProductListExchange;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

public class GetProductsByCategoryIdAsyncTask extends AbstractAsyncTask<Category, ProductListExchange> {

	private Category category;

	public GetProductsByCategoryIdAsyncTask(Activity activity) {
		this(activity, false);
	}

	public GetProductsByCategoryIdAsyncTask(Activity activity, boolean showProgressDialog) {
		super(activity, showProgressDialog);
	}

	@Override
	public void postCallback(ProductListExchange result) {
		final List<Product> list = Arrays.asList(result.getList());
		Log.i("FragmentOrderpage", "" + list.size());
		TextView fragmentRoundOrderCount = (TextView) activity.findViewById(R.id.roundOrderCount);
		OrderCountRefresh callback = new OrderCountRefresh(activity, fragmentRoundOrderCount);
		ProductAdapter aa = new ProductAdapter(activity, list, callback);
		GridView products = (GridView) activity.findViewById(R.id.products);
		products.setAdapter(aa);

		TextView categoryName = (TextView) activity.findViewById(R.id.categoryName);
		categoryName.setText(category.getName());

		// products.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Product selected = list.get(position);
		//
		// OrderAlertDialog dialog = new OrderAlertDialog(activity, selected);
		// dialog.show();
		// }
		// });

	}

	@Override
	protected ProductListExchange inBackground(Category... params) {
		category = params[0];
		String url = base_url + "/products/" + DodoroContext.languageCode(activity) + "/" + category.getId();
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<ProductListExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, ProductListExchange.class);

		return responseEntity.getBody();
	}
}
