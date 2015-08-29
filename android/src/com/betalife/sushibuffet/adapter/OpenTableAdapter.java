package com.betalife.sushibuffet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.activity.TakeAwayActivity;
import com.betalife.sushibuffet.dialog.RoundOrderCountAlertDialog;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class OpenTableAdapter extends AAdapter<Diningtable> {

	public OpenTableAdapter(Activity activity) {
		super(activity);
		resourceId = R.layout.adapter_table;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);
		Diningtable result = getItem(position);
		if (result == null) {
			return convertView;
		}

		convertView.setTag(result);
		TextView name = (TextView) convertView.findViewById(R.id.no);
		name.setText(result.getId() + "");

		TextView desc = (TextView) convertView.findViewById(R.id.status);
		if (!result.isAvailable()) {
			desc.setText("{fa-ban}");
			return convertView;
		}

		if (result.getId() == 0) {
			// takeaway
			name.setText(R.string.lbl_takeaway);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(activity, TakeAwayActivity.class);
					activity.startActivity(intent);
				}
			});
			return convertView;
		}

		final Turnover turnover = result.getTurnover();
		if (turnover == null || turnover.isCheckout()) {
			desc.setText("{fa-check}");
			desc.setTextColor(Color.parseColor("#00FF00"));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Diningtable selected = (Diningtable) v.getTag();
					int tableId = selected.getId();
					RoundOrderCountAlertDialog dialog = new RoundOrderCountAlertDialog(getActivity(), tableId);
					dialog.show();
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
					DodoroContext.goToMainActivity(turnover, activity);
				}
			});
		}

		return convertView;
	}

}
