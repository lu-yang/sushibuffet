package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.model.Turnover;

public class ChangeTableTask extends AbstractAsyncTask<Turnover, Boolean> {

	public ChangeTableTask(Activity activity) {
		super(activity);
	}

	@Override
	protected Boolean inBackground(Turnover... params) {
		final String url = base_url + "/changeTable";
		HttpEntity<Turnover> requestEntity = new HttpEntity<Turnover>(params[0], requestHeaders);
		ResponseEntity<Boolean> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
				Boolean.class);
		return responseEntity.getBody();
	}

	@Override
	public void postCallback(Boolean result) {
		Intent intent = new Intent();
		intent.setClass(activity, MainActivity.class);
		activity.startActivity(intent);
	}

}