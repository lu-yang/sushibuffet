package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.betalife.sushibuffet.model.Diningtable;

public class NewTableActivity extends AbstractAsyncActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_table);
		TableAsyncTask tablesTask = new TableAsyncTask();
		tablesTask.execute();
	}

	public void activeTable(View v) {
		Spinner spin = (Spinner) findViewById(R.id.tblNum);
		Diningtable table = (Diningtable) spin.getSelectedItem();
		Spinner spin2 = (Spinner) findViewById(R.id.perNum);
		String num = (String) spin2.getSelectedItem();
		Toast.makeText(this, "table: " + table.getId() + ", people: " + num, Toast.LENGTH_SHORT).show();
	}

	private class TableAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {

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
			ResponseEntity<Diningtable[]> responseEntity = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Diningtable[].class);
			return Arrays.asList(responseEntity.getBody());
		}

	};
}
