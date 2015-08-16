package com.betalife.sushibuffet.dialog;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.asynctask.TakeOrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class CurrentOrdersDialog {

	private AlertDialog.Builder builder;
	private ListView currentOrders;

	public CurrentOrdersDialog(final Activity activity) {

		LayoutInflater layoutInflater = activity.getLayoutInflater();
		View layout = layoutInflater.inflate(R.layout.dialog_current_orders, null);

		currentOrders = (ListView) layout.findViewById(R.id.current_orders);

		final List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		CurrentOrderAdapter oa = new CurrentOrderAdapter(activity, currentOrdersCache);
		currentOrders.setAdapter(oa);
		// currentOrders.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Order selected = currentOrdersCache.get(position);
		//
		// OrderAlertDialog dialog = new OrderAlertDialog(activity,
		// selected.getProduct());
		// dialog.show();
		// }
		// });

		builder = new AlertDialog.Builder(activity);
		builder.setView(layout);

		builder.setPositiveButton(R.string.btn_take_orders, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				TakeOrdersAsyncTask task = new TakeOrdersAsyncTask(activity);
				task.execute();
			}
		});

		builder.setCancelable(true);

	}

	public void show() {
		builder.create().show();
	}

}
