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
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.dialog.ErrorMessageAlertDialog;
import com.betalife.sushibuffet.exchange.BaseExchange;
import com.betalife.sushibuffet.exchange.DodoroException;
import com.betalife.sushibuffet.util.DodoroContext;

public abstract class AbstractAsyncTask<P, T extends BaseExchange> extends AsyncTask<P, Void, T> {

	protected RestTemplate restTemplate;

	protected HttpHeaders requestHeaders;

	private ProgressDialog progressDialog;

	private boolean showProgressDialog;

	protected Activity activity;

	private RestClientException exception;

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
		if (exception != null) {
			Throwable rootCause = exception.getRootCause();
			DodoroException ex = new DodoroException(rootCause);
			ErrorMessageAlertDialog dialog = new ErrorMessageAlertDialog(activity, ex, null);
			dialog.show();
		} else if (result.getException() != null) {
			ErrorMessageAlertDialog dialog = new ErrorMessageAlertDialog(activity, result.getException(),
					null);
			dialog.show();
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
			exception = e;
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
