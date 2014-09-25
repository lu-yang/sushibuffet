package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnovers;

public class NewTableActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_table);
		GetAllTablesAsyncTask task = new GetAllTablesAsyncTask(this);
		task.execute();
	}

	public void activeTable(View v) {
		Spinner spin = (Spinner) findViewById(R.id.tblNum);
		Diningtable table = (Diningtable) spin.getSelectedItem();
		Turnovers turnover = new Turnovers();
		turnover.setTableId(table.getId());
		Toast.makeText(this, "table: " + table.getId(), Toast.LENGTH_SHORT).show();
		OpenTableTask task = new OpenTableTask(this);
		task.execute(turnover);
	}

	private class GetAllTablesAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {

		public GetAllTablesAsyncTask(Activity activity) {
			super(activity);
		}

		@Override
		public void postCallback(List<Diningtable> result) {
			ArrayAdapter<Diningtable> aa = new ArrayAdapter<Diningtable>(NewTableActivity.this,
					android.R.layout.simple_spinner_item, result);
			Spinner spin = (Spinner) findViewById(R.id.tblNum);
			spin.setAdapter(aa);
		}

		@Override
		protected List<Diningtable> doInBackground(Void... params) {
			String url = getString(R.string.base_uri) + "/availableTables";
			System.out.println("url: " + url);

			HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

			ResponseEntity<Diningtable[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Diningtable[].class);
			return Arrays.asList(responseEntity.getBody());
		}

	};

	private class OpenTableTask extends AbstractAsyncTask<Turnovers, Turnovers> {

		public OpenTableTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Turnovers doInBackground(Turnovers... params) {

			Turnovers tur = params[0];

			final String url = getString(R.string.base_uri) + "/openTable";

			HttpEntity<Turnovers> requestEntity = new HttpEntity<Turnovers>(tur, requestHeaders);

			ResponseEntity<Turnovers> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Turnovers.class);

			return responseEntity.getBody();

		}

		@Override
		public void postCallback(Turnovers result) {
			Intent intent = new Intent();
			intent.setClass(NewTableActivity.this, MainActivity.class);
			startActivity(intent);
		}

	}
}
