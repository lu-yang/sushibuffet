package com.betalife.sushibuffet.adapter;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.AbstractAsyncTask;
import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnovers;

public class TableAdapter extends AAdapter<Diningtable> {

	public TableAdapter(Activity activity, List<Diningtable> tables) {
		super(activity, tables);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Diningtable selected = (Diningtable) v.getTag();
			Turnovers turnover = new Turnovers();
			turnover.setTableId(selected.getId());
			Toast.makeText(activity, "table: " + selected.getId(), Toast.LENGTH_SHORT).show();
			OpenTableTask task = new OpenTableTask(activity);
			task.execute(turnover);
		}
	};

	private class OpenTableTask extends AbstractAsyncTask<Turnovers, Turnovers> {

		public OpenTableTask(Activity activity) {
			super(activity);
		}

		@Override
		protected Turnovers doInBackground(Turnovers... params) {
			Turnovers tur = params[0];
			final String url = activity.getString(R.string.base_uri) + "/openTable";
			HttpEntity<Turnovers> requestEntity = new HttpEntity<Turnovers>(tur, requestHeaders);
			ResponseEntity<Turnovers> responseEntity = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Turnovers.class);
			return responseEntity.getBody();
		}

		@Override
		public void postCallback(Turnovers result) {
			Intent intent = new Intent();
			intent.setClass(activity, MainActivity.class);
			activity.startActivity(intent);
		}

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
			if (result.isAvailable() && result.isCheckout()) {
				desc.setText("可以开桌");
				convertView.setOnClickListener(onClickListener);
			} else {
				desc.setText("不可以开桌");
			}

		}

		return convertView;
	}

}
