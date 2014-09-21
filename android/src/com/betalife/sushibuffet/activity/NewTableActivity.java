package com.betalife.sushibuffet.activity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
		Spinner spin2 = (Spinner) findViewById(R.id.perNum);
		String num = (String) spin2.getSelectedItem();
		Turnovers turnover = new Turnovers();
		turnover.setTableId(table.getId());
		turnover.setCustomerCount(Integer.parseInt(num));
		Toast.makeText(this, "table: " + table.getId() + ", people: " + num, Toast.LENGTH_SHORT).show();
		OpenTableTask task = new OpenTableTask(this);
		task.execute(turnover);

		Spinner spin3 = (Spinner) findViewById(R.id.language);
		String language = (String) spin3.getSelectedItem();
		changeLanguage(language);

	}

	public void changeLanguage(String language) {
		Map<String, Locale> languageMap = new HashMap<String, Locale>();
		languageMap.put(getString(R.string.CHINESE), Locale.CHINESE);
		languageMap.put(getString(R.string.JAPANESE), Locale.JAPANESE);
		languageMap.put(getString(R.string.ENGLISH), Locale.ENGLISH);
		languageMap.put(getString(R.string.FRENCH), Locale.FRENCH);
		languageMap.put(getString(R.string.GERMAN), Locale.GERMAN);
		languageMap.put(getString(R.string.DUTCH), new Locale("nl"));

		Resources resources = getResources();
		Configuration config = resources.getConfiguration();
		DisplayMetrics dm = resources.getDisplayMetrics();
		config.locale = languageMap.get(language);
		resources.updateConfiguration(config, dm);
		recreate();
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
			// TODO Auto-generated method stub

		}

	}
}
