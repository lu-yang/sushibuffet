/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betalife.sushibuffet.activity;

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

/**
 * @author Roy Clarkson
 * @author Pierre-Yves Ricau
 */
public abstract class AbstractAsyncActivity extends Activity implements AsyncActivity {

	protected static final String TAG = AbstractAsyncActivity.class.getSimpleName();

	private ProgressDialog progressDialog;

	private boolean destroyed = false;

	// ***************************************
	// Activity methods
	// ***************************************
	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.destroyed = true;
	}

	// ***************************************
	// Public methods
	// ***************************************
	public void showLoadingProgressDialog() {
		this.showProgressDialog("Loading. Please wait...");
	}

	public void showProgressDialog(CharSequence message) {
		if (this.progressDialog == null) {
			this.progressDialog = new ProgressDialog(this);
			this.progressDialog.setIndeterminate(true);
		}

		this.progressDialog.setMessage(message);
		this.progressDialog.show();
	}

	public void dismissProgressDialog() {
		if (this.progressDialog != null && !this.destroyed) {
			this.progressDialog.dismiss();
		}
	}

	public abstract class AbstractAsyncTask<P, T> extends AsyncTask<P, Void, T> {

		protected RestTemplate restTemplate;

		protected HttpHeaders requestHeaders;

		public AbstractAsyncTask() {
			requestHeaders = new HttpHeaders();
			List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
			acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
			requestHeaders.setAccept(acceptableMediaTypes);
			HttpBasicAuthentication authentication = new HttpBasicAuthentication(
					getString(R.string.username), getString(R.string.password));
			requestHeaders.setAuthorization(authentication);

			restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		}

		@Override
		protected void onPreExecute() {
			showLoadingProgressDialog();
		}

		@Override
		protected void onPostExecute(T result) {
			dismissProgressDialog();
			postCallback(result);
		}

		public abstract void postCallback(T result);

	}

}
