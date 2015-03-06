package com.betalife.sushibuffet.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.asynctask.ChangeTableTask;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class ChangeTableAdapter extends AAdapter<Diningtable> {

	public ChangeTableAdapter(Activity activity) {
		super(activity);
		resourceId = R.layout.adapter_table;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);
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
							Turnover turnover = DodoroContext.getInstance().getTurnover();
							int tableId = selected.getId();
							turnover.setTableId(tableId);
							Toast.makeText(activity, "table: " + tableId, Toast.LENGTH_SHORT).show();
							ChangeTableTask task = new ChangeTableTask(activity);
							task.execute(turnover);
						}
					});
				} else {
					desc.setText("{fa-cutlery}");
					desc.setTextColor(Color.parseColor("#FF0000"));
				}
			}

		}

		return convertView;
	}

}
