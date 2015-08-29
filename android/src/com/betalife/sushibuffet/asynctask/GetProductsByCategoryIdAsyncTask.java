package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.util.Log;
import android.widget.GridView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.exchange.ProductListExchange;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

public class GetProductsByCategoryIdAsyncTask extends AbstractAsyncTask<Integer, ProductListExchange> {

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
		ProductAdapter aa = new ProductAdapter(activity, list);
		GridView products = (GridView) activity.findViewById(R.id.products);
		products.setAdapter(aa);
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
	protected ProductListExchange inBackground(Integer... params) {
		String url = base_url + "/products/" + DodoroContext.languageCode(activity) + "/" + params[0];
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<ProductListExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, ProductListExchange.class);

		return responseEntity.getBody();
	}
}
