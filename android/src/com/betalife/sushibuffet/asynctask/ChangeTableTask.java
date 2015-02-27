package com.betalife.sushibuffet.asynctask;

import android.app.Activity;
import android.content.Intent;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.exchange.BooleanExchange;

public class ChangeTableTask extends UpdateTurnoverTask {

	public ChangeTableTask(Activity activity) {
		super(activity);
	}

	@Override
	public void postCallback(BooleanExchange result) {
		Intent intent = new Intent();
		intent.setClass(activity, MainActivity.class);
		activity.startActivity(intent);
	}

}