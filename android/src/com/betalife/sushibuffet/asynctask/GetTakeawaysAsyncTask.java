package com.betalife.sushibuffet.asynctask;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.widget.GridView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.TakeawayAdapter;
import com.betalife.sushibuffet.exchange.TakeawayListExchange;

public class GetTakeawaysAsyncTask extends AbstractAsyncTask<Void, TakeawayListExchange> {
	String url = base_url + "/takeaways";

	private TakeawayAdapter adapter;

	public GetTakeawaysAsyncTask(Activity activity) {
		super(activity, true);
		adapter = new TakeawayAdapter(activity);
	}

	@Override
	public void postCallback(TakeawayListExchange result) {
		adapter.setList(Arrays.asList(result.getList()));
		GridView tables = (GridView) activity.findViewById(R.id.grid_takeaway);
		tables.setAdapter(adapter);
	}

	@Override
	protected TakeawayListExchange inBackground(Void... params) {
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
		ResponseEntity<TakeawayListExchange> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
				requestEntity, TakeawayListExchange.class);
		return responseEntity.getBody();
	}

};