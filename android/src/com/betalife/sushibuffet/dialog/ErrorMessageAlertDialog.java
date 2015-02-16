package com.betalife.sushibuffet.dialog;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestClientException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.Callback;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.exchange.DodoroException;
import com.betalife.sushibuffet.util.DodoroContext;

public class ErrorMessageAlertDialog {

	private AlertDialog.Builder builder;

	public ErrorMessageAlertDialog(final Activity parent, final DodoroException ex, final Callback callback) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_error_message, null);

		TextView errorType = (TextView) view.findViewById(R.id.error_type);
		if (StringUtils.equals(ex.getType(), "jpos.JposException")) {
			errorType.setText(R.string.err_pos_error);
		} else if (StringUtils.equals(ex.getType(), RestClientException.class.getName())) {
			errorType.setText(R.string.err_other_error);
		} else {
			errorType.setText(R.string.err_server_error);
		}

		final TextView errorDetail = (TextView) view.findViewById(R.id.error_detail);
		errorDetail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				errorDetail.setText(ex.getMessage() + "\n" + ex.getStackTracePart());
			}
		});

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string.label_restart_app, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (callback != null) {
					callback.callback();
				}
				DodoroContext.restartApp(parent);
			}
		});

		builder.setCancelable(true);

	}

	public void show() {
		builder.create().show();
	}

}
