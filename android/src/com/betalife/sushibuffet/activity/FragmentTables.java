package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.adapter.AAdapter;
import com.betalife.sushibuffet.model.Diningtable;

public class FragmentTables extends Fragment {

	private AAdapter<Diningtable> adapter;

	public FragmentTables(AAdapter<Diningtable> adapter) {
		super();
		this.adapter = adapter;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_tables, container, false);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this.getActivity());
		task.execute();
	}

	private class GetAllTablesAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {

		public GetAllTablesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(final List<Diningtable> result) {
			adapter.setList(result);
			View fragmentTablesView = FragmentTables.this.getView();
			GridView tables = (GridView) fragmentTablesView.findViewById(R.id.diningtables);
			tables.setAdapter(adapter);
		}

		@Override
		protected List<Diningtable> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/availableTables";
			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);
			ResponseEntity<Diningtable[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Diningtable[].class);
			return Arrays.asList(responseEntity.getBody());
		}

	};

}
