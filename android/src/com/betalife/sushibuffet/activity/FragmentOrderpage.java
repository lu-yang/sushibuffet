package com.betalife.sushibuffet.activity;

import java.util.Arrays;
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
import com.betalife.sushibuffet.model.Categories;
import com.betalife.sushibuffet.model.Products;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */

/* 菜单，点餐页面 */
public class FragmentOrderpage extends Fragment {

	public FragmentOrderpage() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		GetRootCategoriesAsyncTask task = new GetRootCategoriesAsyncTask(getActivity());
		task.execute();
		// TODO
		ProductsAsyncTask task2 = new ProductsAsyncTask(getActivity(), 2);
		task2.execute();
		return inflater.inflate(R.layout.fragment_orderpage, container, false);
	}

	private class GetRootCategoriesAsyncTask extends AbstractAsyncTask<Void, List<Categories>> {

		public GetRootCategoriesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(final List<Categories> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			CategoriesAdapter aa = new CategoriesAdapter(activity, result);
			ListView categories = (ListView) activity.findViewById(R.id.categories);
			categories.setAdapter(aa);
			categories.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					Categories selected = result.get(position);

					ProductsAsyncTask task2 = new ProductsAsyncTask(getActivity(), selected.getId());
					task2.execute();
				}
			});
		}

		@Override
		protected List<Categories> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/rootCategories";
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Categories[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Categories[].class);
			List<Categories> rootCategories = Arrays.asList(responseEntity.getBody());

			return rootCategories;
		}
	};

	private class ProductsAsyncTask extends AbstractAsyncTask<Void, List<Products>> {

		private int categoryId;

		public ProductsAsyncTask(Activity activity, int categoryId) {
			super(activity);
			this.categoryId = categoryId;
		}

		@Override
		public void postCallback(List<Products> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			ProductsAdapter aa = new ProductsAdapter(activity, result);
			GridView products = (GridView) activity.findViewById(R.id.products);
			products.setAdapter(aa);
		}

		@Override
		protected List<Products> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/products/" + categoryId;
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Products[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Products[].class);
			List<Products> products = Arrays.asList(responseEntity.getBody());

			return products;
		}
	};
}
