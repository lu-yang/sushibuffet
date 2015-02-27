package com.betalife.sushibuffet.asynctask;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.util.DodoroContext;

public class CheckoutTask extends UpdateTurnoverTask {

	public CheckoutTask(Activity activity) {
		super(activity);
	}

	@Override
	public void postCallback(BooleanExchange result) {
		Toast.makeText(activity, activity.getString(R.string.setting_activity_checkout_ok),
				Toast.LENGTH_SHORT).show();
		DodoroContext.getInstance().setTurnover(null);

		// restart app
		DodoroContext.restartApp(activity);

	}
}