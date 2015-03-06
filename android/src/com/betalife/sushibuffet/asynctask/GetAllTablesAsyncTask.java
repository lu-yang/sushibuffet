package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.betalife.sushibuffet.activity.TableActivity;
import com.betalife.sushibuffet.exchange.DiningtableListExchange;

public class GetAllTablesAsyncTask extends AbstractAsyncTask<Void, DiningtableListExchange> {

	String url = base_url + "/availableTables";
	private TableActivity activity;

	public GetAllTablesAsyncTask(TableActivity activity) {
		this(activity, false);
	}

	public GetAllTablesAsyncTask(TableActivity activity, boolean showProgressDialog) {
		super(activity, showProgressDialog);
		this.activity = activity;
	}

	@Override
	public void postCallback(DiningtableListExchange result) {
		activity.displayTables(Arrays.asList(result.getList()));
	}

	@Override
	protected DiningtableListExchange inBackground(Void... params) {
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<DiningtableListExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, DiningtableListExchange.class);
		return responseEntity.getBody();
	}

};