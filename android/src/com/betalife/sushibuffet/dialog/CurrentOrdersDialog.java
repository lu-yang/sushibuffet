package com.betalife.sushibuffet.dialog;

import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.OrderCountRefresh;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.asynctask.TakeOrdersAsyncTask;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class CurrentOrdersDialog extends Dialog {

	private Activity activity;
	private TextView sendBtn;

	public CurrentOrdersDialog(Activity activity, boolean cancelable) {
		super(activity);
		this.activity = activity;
		setCancelable(cancelable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* no-need title */
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_current_orders);
		final DodoroContext instance = DodoroContext.getInstance();
		Resources resources = activity.getResources();

		/* no-need title */
		// setTitle(resources.getString(R.string.lbl_round,
		// instance.getTurnover().getRound()));

		TextView round = (TextView) findViewById(R.id.round);
		instance.fillCurrentRound(resources, round);
		TextView roundOrderCount = (TextView) findViewById(R.id.roundOrderCount);
		instance.fillRoundOrderCount(resources, roundOrderCount);

		TextView fragmentRoundOrderCount = (TextView) activity.findViewById(R.id.roundOrderCount);

		OrderCountRefresh callback = new OrderCountRefresh(activity, roundOrderCount, fragmentRoundOrderCount);

		ListView currentOrders = (ListView) findViewById(R.id.current_orders);
		final List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		CurrentOrderAdapter oa = new CurrentOrderAdapter(activity, currentOrdersCache, callback);
		currentOrders.setAdapter(oa);

		sendBtn = (TextView) findViewById(R.id.ok);
		if (instance.isInRoundInterval()) {
			sendBtn.setClickable(false);
			long millisInFuture = DodoroContext.getInstance().getInRoundInterval();
			CountDownTimer timer = new CountDownTimer(millisInFuture, 1000) {

				@Override
				public void onFinish() {
					activeSendBtn();
				}

				@Override
				public void onTick(long millisUntilFinished) {
					sendBtn.setText(activity.getString(R.string.lbl_round_interval_left,
							millisUntilFinished / 1000));
				}

			};

			timer.start();

		} else {
			activeSendBtn();
		}

	}

	private void activeSendBtn() {
		sendBtn.setClickable(true);
		sendBtn.setText(R.string.btn_order_ok);
		final DodoroContext instance = DodoroContext.getInstance();
		sendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (instance.isOverRoundOrderCount()) {
					Toast.makeText(activity, R.string.err_round_order_count, Toast.LENGTH_SHORT).show();
					return;
				}

				if (instance.isInRoundInterval()) {
					Toast.makeText(
							activity,
							activity.getString(R.string.lbl_round_interval, instance.getConstant()
									.getRoundInterval()), Toast.LENGTH_SHORT).show();
					return;
				}

				TakeOrdersAsyncTask task = new TakeOrdersAsyncTask(activity);
				task.execute();
				cancel();
			}
		});

	}
}
