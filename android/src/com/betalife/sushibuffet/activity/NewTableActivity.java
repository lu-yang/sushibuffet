package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.betalife.sushibuffet.model.Categories;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnovers;
import com.betalife.sushibuffet.util.DodoroApplication;

public class NewTableActivity extends AbstractAsyncActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_table);
		TablesAsyncTask task = new TablesAsyncTask();
		task.execute();
	}

	public void activeTable(View v) {
		Spinner spin = (Spinner) findViewById(R.id.tblNum);
		Diningtable table = (Diningtable) spin.getSelectedItem();
		Spinner spin2 = (Spinner) findViewById(R.id.perNum);
		String num = (String) spin2.getSelectedItem();
		Turnovers turnover = new Turnovers();
		turnover.setTableId(table.getId());
		turnover.setCustomerCount(Integer.parseInt(num));
		Toast.makeText(this, "table: " + table.getId() + ", people: " + num, Toast.LENGTH_SHORT).show();
		OpenTableTask task = new OpenTableTask();
		task.execute(turnover);

		Spinner spin3 = (Spinner) findViewById(R.id.language);
		String language = (String) spin3.getSelectedItem();
		changeLanguage(language);

	}

	public void changeLanguage(String language) {
		Resources resources = getResources();
		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		config.locale = DodoroApplication.getLocale(language);
		resources.updateConfiguration(config, dm);
		recreate();
	}

	private class TablesAsyncTask extends AbstractAsyncTask<Void, List<Diningtable>> {

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

	private class OpenTableTask extends AbstractAsyncTask<Turnovers, List<Categories>> {

		@Override
		protected List<Categories> doInBackground(Turnovers... params) {

			Turnovers tur = params[0];

			final String url = getString(R.string.base_uri) + "/openTable";

			HttpEntity<Turnovers> requestEntity = new HttpEntity<Turnovers>(tur, requestHeaders);

			ResponseEntity<Categories[]> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Categories[].class);

			return Arrays.asList(responseEntity.getBody());

		}

		@Override
		public void postCallback(List<Categories> result) {
			// TODO Auto-generated method stub

		}

	}
}
