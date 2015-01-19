package com.betalife.sushibuffet.asynctask;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpBasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.util.Log;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.util.DodoroContext;

public abstract class AbstractAsyncTask<P, T> extends AsyncTask<P, Void, T> {

	protected RestTemplate restTemplate;

	protected HttpHeaders requestHeaders;

	private ProgressDialog progressDialog;

	private boolean showProgressDialog;

	protected Activity activity;

	private boolean exception;

	protected String base_url;

	public AbstractAsyncTask(Activity activity, boolean showProgressDialog) {
		this(activity);
		this.showProgressDialog = showProgressDialog;
	}

	public AbstractAsyncTask(Activity activity) {
		this.activity = activity;

		SharedPreferences preferences = activity.getPreferences(Activity.MODE_PRIVATE);
		base_url = preferences.getString("base_url", null);
		if (StringUtils.isEmpty(base_url)) {
			base_url = activity.getString(R.string.base_uri);
			Editor editor = preferences.edit();
			editor.putString("base_url", base_url);
			editor.putString("comment", "格式如：http://192.168.0.101:8080/sushibuffet");
			editor.commit();
		}

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
		if (showProgressDialog) {
			showProgressDialog();
		}
	}

	private void showProgressDialog() {
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
		if (exception) {

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setMessage(R.string.err_server_error);
			builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// restart app
					DodoroContext.restartApp(activity);
				}
			});

			builder.setCancelable(false);

			builder.create().show();

		} else {
			postCallback(result);
		}
	}

	public abstract void postCallback(T result);

	final protected T doInBackground(P... params) {
		try {
			return inBackground(params);
		} catch (RestClientException e) {
			Log.e(this.getClass().getName(), e.getMessage(), e);
			exception = true;
		}
		return null;
	}

	protected abstract T inBackground(P... params);

	public ProgressDialog getProgressDialog() {
		return progressDialog;
	}

	public void setProgressDialog(ProgressDialog progressDialog) {
		this.progressDialog = progressDialog;
	}

}
