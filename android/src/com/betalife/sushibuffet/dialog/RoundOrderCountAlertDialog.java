package com.betalife.sushibuffet.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.CallbackResult;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class RoundOrderCountAlertDialog {

	private AlertDialog.Builder builder;

	public RoundOrderCountAlertDialog(final Activity parent, final int tableId,
			final CallbackResult<Integer> callback) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_round_count, null);

		TextView tableNo = (TextView) view.findViewById(R.id.tableNo);
		tableNo.setText("" + tableId);
		final NumberPicker roundOrderCount = (NumberPicker) view.findViewById(R.id.roundOrderCount);
		roundOrderCount.setMaxValue(24);
		roundOrderCount.setMinValue(1);
		Turnover turnover = DodoroContext.getInstance().getTurnover();
		if (turnover == null) {
			roundOrderCount.setValue(9);
		} else {
			roundOrderCount.setValue(turnover.getRoundOrderCount());
		}

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int value = roundOrderCount.getValue();
				callback.callback(value);
			}
		});

		builder.setCancelable(true);

	}

	public void show() {
		builder.create().show();
	}

}
