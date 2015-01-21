package com.betalife.sushibuffet.asynctask;

import java.util.ArrayList;
import java.util.List;

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
		base_url = DodoroContext.getInstance().getServerAddress(activity);
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
