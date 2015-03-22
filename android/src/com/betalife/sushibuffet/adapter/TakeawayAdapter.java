package com.betalife.sushibuffet.adapter;

import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.activity.TotalPriceView;
import com.betalife.sushibuffet.model.TakeawayExt;
import com.betalife.sushibuffet.util.DodoroContext;

public class TakeawayAdapter extends AAdapter<TakeawayExt> {
	private SimpleDateFormat sdf;

	public TakeawayAdapter(Activity activity) {
		super(activity);
		resourceId = R.layout.adapter_takeaway;
		sdf = new SimpleDateFormat(activity.getString(R.string.order_history_format));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = initConvertView(convertView, parent);

		final TakeawayExt model = getItem(position);
		if (model == null) {
			return convertView;
		}

		convertView.setTag(model);
		TextView takeawayno = (TextView) convertView.findViewById(R.id.takeawayno);
		takeawayno.setText("no. " + model.getId());

		TextView desc = (TextView) convertView.findViewById(R.id.desc);
		desc.setText(model.getMemo());

		TextView createDate = (TextView) convertView.findViewById(R.id.createDate);
		String format = sdf.format(model.getCreated());
		createDate.setText(format);

		TextView status = (TextView) convertView.findViewById(R.id.status);
		if (model.isTakeaway()) {
			status.setText("已取走");
		} else if (model.getTurnover().isCheckout()) {
			status.setText("已结账，未取走");
		} else {
			status.setText("未结账");
		}

		TotalPriceView totalPriceView = new TotalPriceView(convertView);
		totalPriceView.setTotalPrice(model.getTotal(), model.getTurnover().getDiscount());

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DodoroContext.getInstance().setTakeaway(model);
				Intent intent = new Intent();
				intent.setClass(activity, MainActivity.class);
				activity.startActivity(intent);
			}
		});
		return convertView;

	}
}
