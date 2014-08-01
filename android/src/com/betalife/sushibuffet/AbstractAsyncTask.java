package com.betalife.sushibuffet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.betalife.sushibuffet.activity.R;

public abstract class AbstractAsyncTask<P, T> extends AsyncTask<P, Void, T> {

	protected RestTemplate restTemplate;

	protected HttpHeaders requestHeaders;

	private ProgressDialog progressDialog;

	protected Activity activity;

	public AbstractAsyncTask(Activity activity) {
		this.activity = activity;
		requestHeaders = new HttpHeaders();
		List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
		acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(acceptableMediaTypes);
		HttpBasicAuthentication authentication = new HttpBasicAuthentication(
				activity.getString(R.string.username), activity.getString(R.string.password));
		requestHeaders.setAuthorization(authentication);

		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	@Override
	protected void onPreExecute() {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(activity);
			this.progressDialog.setIndeterminate(true);
		}

		String message = "Loading. Please wait...";
		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	@Override
	protected void onPostExecute(T result) {
		if (this.progressDialog != null && !activity.isDestroyed()) {
			this.progressDialog.dismiss();
		}
		postCallback(result);
	}

	public abstract void postCallback(T result);

}
