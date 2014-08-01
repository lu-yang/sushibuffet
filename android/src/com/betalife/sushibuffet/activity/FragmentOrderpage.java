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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.model.Categories;

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
		return inflater.inflate(R.layout.fragment_orderpage, container, false);
	}

	private class GetRootCategoriesAsyncTask extends AbstractAsyncTask<Void, List<Categories>> {

		public GetRootCategoriesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(List<Categories> result) {
			Log.i("FragmentOrderpage", "" + result.size());
			ArrayAdapter<Categories> aa = new ArrayAdapter<Categories>(activity,
					android.R.layout.simple_spinner_item, result);
			Spinner spin = (Spinner) activity.findViewById(R.id.categories);
			spin.setAdapter(aa);
		}

		@Override
		protected List<Categories> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/rootCategories";
			Log.d("FragmentOrderpage", "url: " + url);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			ResponseEntity<Categories[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Categories[].class);
			return Arrays.asList(responseEntity.getBody());
		}
	};
}
