package com.betalife.sushibuffet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.util.DodoroContext;

public class TakeawayAdapter extends AAdapter<Takeaway> {

	public TakeawayAdapter(Activity activity) {
		super(activity);
		resourceId = R.layout.adapter_takeaway;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		final Takeaway result = getItem(position);
		if (result == null) {
			return convertView;
		}

		convertView.setTag(result);
		TextView takeawayno = (TextView) convertView.findViewById(R.id.takeawayno);
		takeawayno.setText("no. " + result.getId());

		TextView desc = (TextView) convertView.findViewById(R.id.desc);
		desc.setText(result.getMemo());

		TextView createDate = (TextView) convertView.findViewById(R.id.createDate);
		createDate.setText(DateFormat.format(activity.getString(R.string.order_history_format),
				result.getCreated()));

		TextView status = (TextView) convertView.findViewById(R.id.status);
		if (result.isTakeaway()) {
			status.setText("已取走");
		} else if (result.getTurnover().isCheckout()) {
			status.setText("已结账，未取走");
		} else {
			status.setText("未结账");
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DodoroContext.getInstance().setTakeaway(result);
				Intent intent = new Intent();
				intent.setClass(activity, MainActivity.class);
				activity.startActivity(intent);
			}
		});
		return convertView;

	}
}
