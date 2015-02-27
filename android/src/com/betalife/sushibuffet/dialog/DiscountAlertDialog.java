package com.betalife.sushibuffet.dialog;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.Callback;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.asynctask.ChangeDiscountTask;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class DiscountAlertDialog {

	private AlertDialog.Builder builder;

	public DiscountAlertDialog(final Activity parent, final Callback callback) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_discount, null);

		final TextView discount = (TextView) view.findViewById(R.id.discount);

		Turnover turnover = DodoroContext.getInstance().getTurnover();
		setDiscount("" + turnover.getDiscount(), discount);

		List<Button> list = new LinkedList<Button>();
		list.add((Button) view.findViewById(R.id.one));
		list.add((Button) view.findViewById(R.id.two));
		list.add((Button) view.findViewById(R.id.three));
		list.add((Button) view.findViewById(R.id.four));
		list.add((Button) view.findViewById(R.id.five));
		list.add((Button) view.findViewById(R.id.six));
		list.add((Button) view.findViewById(R.id.seven));
		list.add((Button) view.findViewById(R.id.eight));
		list.add((Button) view.findViewById(R.id.nine));
		list.add((Button) view.findViewById(R.id.zero));

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				String digital = button.getText().toString();
				if (discount.getTag() == null) {
					setDiscount(digital, discount);
					return;
				}
				Integer current = (Integer) discount.getTag();
				if (current == 0) {
					setDiscount(digital, discount);
					return;
				}

				int num = NumberUtils.toInt(current + digital);
				if (num < 100) {
					setDiscount("" + num, discount);
				}
			}
		};
		for (Button button : list) {
			button.setOnClickListener(listener);
		}

		Button backspace = (Button) view.findViewById(R.id.backspace);
		backspace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (discount.getTag() == null) {
					return;
				}
				Integer current = (Integer) discount.getTag();
				current /= 10;
				if (current == 0) {
					setDiscount(null, discount);
				} else {
					setDiscount("" + current, discount);
				}
			}

		});

		Button clear = (Button) view.findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setDiscount(null, discount);
			}

		});

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Integer value = discount.getTag() == null ? 100 : (Integer) discount.getTag();

				Turnover turnover = DodoroContext.getInstance().getTurnover();
				turnover.setDiscount(value);
				ChangeDiscountTask task = new ChangeDiscountTask(parent);
				task.execute(turnover);
			}
		});

		builder.setCancelable(true);

	}

	private void setDiscount(String value, TextView discount) {
		if (StringUtils.isEmpty(value)) {
			discount.setText(null);
			discount.setTag(null);
		} else {
			discount.setText(value + "%");
			discount.setTag(NumberUtils.toInt(value));
		}
	}

	public void show() {
		builder.create().show();
	}

}
