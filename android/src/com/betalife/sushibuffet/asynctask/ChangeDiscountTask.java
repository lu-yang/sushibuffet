package com.betalife.sushibuffet.asynctask;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;

public class ChangeDiscountTask extends UpdateTurnoverTask {

	public ChangeDiscountTask(Activity activity) {
		super(activity);
	}

	@Override
	public void postCallback(BooleanExchange result) {
		Toast.makeText(activity, activity.getString(R.string.setting_activity_changeDiscount_mes),
				Toast.LENGTH_SHORT).show();
	}

}