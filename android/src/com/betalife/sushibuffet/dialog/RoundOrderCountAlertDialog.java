package com.betalife.sushibuffet.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.asynctask.OpenTableTask;
import com.betalife.sushibuffet.model.Turnover;

public class RoundOrderCountAlertDialog {

	private AlertDialog.Builder builder;

	public RoundOrderCountAlertDialog(final Activity parent, final int tableId) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_round_count, null);

		TextView tableNo = (TextView) view.findViewById(R.id.tableNo);
		tableNo.setText("" + tableId);
		final NumberPicker roundOrderCount = (NumberPicker) view.findViewById(R.id.roundOrderCount);
		roundOrderCount.setMaxValue(24);
		roundOrderCount.setMinValue(1);
		roundOrderCount.setValue(9);

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int value = roundOrderCount.getValue();
				Turnover turnover = new Turnover();
				turnover.setRoundOrderCount(value);
				turnover.setTableId(tableId);
				Toast.makeText(parent, "table: " + tableId + ", Count per Round: " + value,
						Toast.LENGTH_SHORT).show();
				OpenTableTask task = new OpenTableTask(parent);
				task.execute(turnover);
			}
		});

		builder.setCancelable(true);

	}

	public void show() {
		builder.create().show();
	}

}
