package com.betalife.sushibuffet.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.asynctask.TakeOrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class CurrentOrdersDialog extends Dialog {

	private Activity activity;

	public CurrentOrdersDialog(Activity activity, boolean cancelable) {
		super(activity);
		this.activity = activity;
		setCancelable(cancelable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_current_orders);
		final DodoroContext instance = DodoroContext.getInstance();
		Resources resources = activity.getResources();
		setTitle(resources.getString(R.string.lbl_round, instance.getTurnover().getRound()));

		TextView roundOrderCount = (TextView) findViewById(R.id.roundOrderCount);
		instance.fillRoundOrderCount(resources, roundOrderCount);

		ListView currentOrders = (ListView) findViewById(R.id.current_orders);
		final List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		CurrentOrderAdapter oa = new CurrentOrderAdapter(activity, currentOrdersCache);
		currentOrders.setAdapter(oa);

		Button ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (instance.isOverRoundOrderCount()) {
					Toast.makeText(activity, R.string.err_round_order_count, Toast.LENGTH_SHORT).show();
					return;
				}

				TakeOrdersAsyncTask task = new TakeOrdersAsyncTask(activity);
				task.execute();
				cancel();
			}
		});
	}
}
