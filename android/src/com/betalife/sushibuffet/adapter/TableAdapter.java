package com.betalife.sushibuffet.adapter;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;



public class TableAdapter extends AAdapter<Diningtable> {

	public TableAdapter(Activity activity, List<Diningtable> tables) {
		super(activity, tables);
	}

	private class OpenTableTask extends AbstractAsyncTask<Turnover, Turnover> {

		public OpenTableTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Turnover doInBackground(Turnover... params) {
			Turnover tur = params[0];
			final String url = activity.getString(R.string.base_uri) + "/openTable";
			HttpEntity<Turnover> requestEntity = new HttpEntity<Turnover>(tur, requestHeaders);
			ResponseEntity<Turnover> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Turnover.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Turnover result) {
			goToMainActivity(result);
		}

	}

	private void goToMainActivity(Turnover turnover) {
		DodoroContext.getInstance().setTurnover(turnover);

		Intent intent = new Intent();
		intent.setClass(activity, MainActivity.class);
		activity.startActivity(intent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = this.layoutInflater.inflate(R.layout.table, parent, false);
		}
		Diningtable result = getItem(position);
		convertView.setTag(result);
		if (result != null) {
			TextView name = (TextView) convertView.findViewById(R.id.no);
			name.setText(result.getId() + "");

			TextView desc = (TextView) convertView.findViewById(R.id.status);
			if (!result.isAvailable()) {
				desc.setText("{fa-ban}");
				
			} else {
				final Turnover turnover = result.getTurnover();
				if (turnover == null || turnover.isCheckout()) {
					desc.setText("{fa-check}");
					desc.setTextColor(Color.parseColor("#00FF00"));
					convertView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Diningtable selected = (Diningtable) v.getTag();
							Turnover turnover = new Turnover();
							int tableId = selected.getId();
							turnover.setTableId(tableId);
							Toast.makeText(activity, "table: " + tableId, Toast.LENGTH_SHORT).show();
							OpenTableTask task = new OpenTableTask(activity);
							task.execute(turnover);
						}
					});
				} else {
					desc.setText("{fa-cutlery}");
					desc.setTextColor(Color.parseColor("#FF0000"));
					convertView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							Diningtable selected = (Diningtable) v.getTag();
							Toast.makeText(activity, "table: " + selected.getId(), Toast.LENGTH_SHORT).show();
							goToMainActivity(turnover);
						}
					});
				}
			}

		}

		return convertView;
	}

}
