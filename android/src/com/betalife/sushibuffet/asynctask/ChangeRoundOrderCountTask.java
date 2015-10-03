package com.betalife.sushibuffet.asynctask;

import android.app.Activity;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class ChangeRoundOrderCountTask extends UpdateTurnoverTask {

	public ChangeRoundOrderCountTask(Activity activity) {
		super(activity);
	}

	@Override
	public void postCallback(BooleanExchange result) {
		Turnover turnover = DodoroContext.getInstance().getTurnover();
		Toast.makeText(
				activity,
				activity.getString(R.string.msg_round_order_count, turnover.getTableId(),
						turnover.getRoundOrderCount()), Toast.LENGTH_SHORT).show();
	}
}