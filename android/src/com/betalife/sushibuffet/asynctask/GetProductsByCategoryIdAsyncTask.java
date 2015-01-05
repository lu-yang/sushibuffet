package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.dialog.OrderAlertDialog;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

public class GetProductsByCategoryIdAsyncTask extends AbstractAsyncTask<Void, List<Product>> {

	private int categoryId;

	public GetProductsByCategoryIdAsyncTask(Activity activity, int categoryId) {
		this(activity, categoryId, false);
	}

	public GetProductsByCategoryIdAsyncTask(Activity activity, int categoryId, boolean showProgressDialog) {
		super(activity, showProgressDialog);
		this.categoryId = categoryId;
	}

	@Override
	public void postCallback(final List<Product> result) {
		Log.i("FragmentOrderpage", "" + result.size());
		ProductAdapter aa = new ProductAdapter(activity, result);
		GridView products = (GridView) activity.findViewById(R.id.products);
		products.setAdapter(aa);
		products.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Product selected = result.get(position);

				OrderAlertDialog dialog = new OrderAlertDialog(activity, selected);
				dialog.show();
			}
		});

	}

	@Override
	protected List<Product> inBackground(Void... params) {
		String url = activity.getString(R.string.base_uri) + "/products/"
				+ DodoroContext.languageCode(activity) + "/" + categoryId;
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Product[].class);
		List<Product> products = Arrays.asList(responseEntity.getBody());

		return products;
	}
}
