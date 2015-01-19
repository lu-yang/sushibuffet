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
import android.widget.ListView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.util.DodoroContext;

public class GetCategoriesAsyncTask extends AbstractAsyncTask<Void, List<Category>> {

	public GetCategoriesAsyncTask(Activity activity) {
		this(activity, false);
	}

	public GetCategoriesAsyncTask(Activity activity, boolean showProgressDialog) {
		super(activity, showProgressDialog);
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

				GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(activity,
						selected.getId());
				task2.execute();
			}
		});
	}

	@Override
	protected List<Category> inBackground(Void... params) {
		String url = base_url + "/categories/" + DodoroContext.languageCode(activity) + "/1";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Category[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Category[].class);
		List<Category> categories = Arrays.asList(responseEntity.getBody());

		return categories;
	}
}
