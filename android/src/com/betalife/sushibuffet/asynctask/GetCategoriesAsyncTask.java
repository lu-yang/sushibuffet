package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;

import com.betalife.sushibuffet.activity.CallbackResult;
import com.betalife.sushibuffet.exchange.CategoryListExchange;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.util.DodoroContext;

public class GetCategoriesAsyncTask extends AbstractAsyncTask<Void, CategoryListExchange> {

	private CallbackResult<List<Category>> callback;

	public GetCategoriesAsyncTask(Activity activity, CallbackResult<List<Category>> callback) {
		this(activity, callback, false);
	}

	public GetCategoriesAsyncTask(Activity activity, CallbackResult<List<Category>> callback,
			boolean showProgressDialog) {
		super(activity, showProgressDialog);
		this.callback = callback;

	}

	@Override
	public void postCallback(CategoryListExchange result) {
		final List<Category> list = Arrays.asList(result.getList());
		callback.callback(list);
	}

	@Override
	protected CategoryListExchange inBackground(Void... params) {
		String url = base_url + "/categories/" + DodoroContext.languageCode(activity) + "/1";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<CategoryListExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, CategoryListExchange.class);

		return responseEntity.getBody();
	}
}
