package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.betalife.sushibuffet.activity.TableActivity;
import com.betalife.sushibuffet.model.Diningtable;

public class GetAllTablesAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {

	private TableActivity activity;

	public GetAllTablesAsyncTask(TableActivity activity) {
		this(activity, false);
	}

	public GetAllTablesAsyncTask(TableActivity activity, boolean showProgressDialog) {
		super(activity, showProgressDialog);
		this.activity = activity;
	}

	@Override
	public void postCallback(final List<Diningtable> result) {
		activity.displayTables(result);
	}

	@Override
	protected List<Diningtable> inBackground(Void... params) {
		String url = base_url + "/availableTables";
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<Diningtable[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, Diningtable[].class);
		return Arrays.asList(responseEntity.getBody());
	}

};