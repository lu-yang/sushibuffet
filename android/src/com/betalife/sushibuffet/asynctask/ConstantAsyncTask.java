package com.betalife.sushibuffet.asynctask;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;

import com.betalife.sushibuffet.activity.ChooseTableActivity;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.util.DodoroContext;

public class ConstantAsyncTask extends AbstractAsyncTask<Void, Constant> {

	public ConstantAsyncTask(Activity activity) {
		super(activity, false);
	}

	@Override
	public void postCallback(Constant result) {
		DodoroContext.getInstance().setConstant(result);
		Intent intent = new Intent();
		intent.setClass(activity, ChooseTableActivity.class);
		activity.startActivity(intent);
	}

	@Override
	protected Constant inBackground(Void... params) {
		String url = base_url + "/constant";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		ResponseEntity<Constant> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
				Constant.class);
		return responseEntity.getBody();
	}
}