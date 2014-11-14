package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.adapter.OrderAdapter;
import com.betalife.sushibuffet.adapter.ProductAdapter;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */

/* 菜单，点餐页面 */
public class FragmentOrderpage extends Fragment implements Refreshable {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_orderpage, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	private class GetCategoriesAsyncTask extends AbstractAsyncTask<Void, List<Category>> {

		public GetCategoriesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(final List<Category> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			CategoryAdapter aa = new CategoryAdapter(activity, result);
			ListView categories = (ListView) activity.findViewById(R.id.categories);
			categories.setAdapter(aa);
			categories.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Category selected = result.get(position);

					GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(
							getActivity(), selected.getId());
					task2.execute();
				}
			});
		}

		@Override
		protected List<Category> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/categories/"
					+ DodoroContext.languageCode(getActivity()) + "/1";
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Category[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Category[].class);
			List<Category> categories = Arrays.asList(responseEntity.getBody());

			return categories;
		}
	}

	private class GetProductsByCategoryIdAsyncTask extends AbstractAsyncTask<Void, List<Product>> {

		private int categoryId;

		public GetProductsByCategoryIdAsyncTask(Activity activity, int categoryId) {
			super(activity);
			this.categoryId = categoryId;
		}

		@Override
		public void postCallback(List<Product> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			ProductAdapter aa = new ProductAdapter(activity, result);
			GridView products = (GridView) activity.findViewById(R.id.products);
			products.setAdapter(aa);
		}

		@Override
		protected List<Product> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/products/"
					+ DodoroContext.languageCode(getActivity()) + "/" + categoryId;
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Product[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Product[].class);
			List<Product> products = Arrays.asList(responseEntity.getBody());

			return products;
		}
	}

	@Override
	public void refresh() {
		GetCategoriesAsyncTask task = new GetCategoriesAsyncTask(getActivity());
		task.execute();
		// TODO
		GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(getActivity(), 2);
		task2.execute();

		OrderAdapter oa = new OrderAdapter(getActivity(), new LinkedList<Order>());
		ListView orders = (ListView) getActivity().findViewById(R.id.list);
		orders.setAdapter(oa);
	}
}
