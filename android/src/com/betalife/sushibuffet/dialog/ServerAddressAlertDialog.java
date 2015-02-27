package com.betalife.sushibuffet.dialog;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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
import com.betalife.sushibuffet.util.DodoroContext;

public class ServerAddressAlertDialog {

	private AlertDialog.Builder builder;

	public ServerAddressAlertDialog(final Activity parent, final Callback callback, boolean cancelable) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_server_address, null);

		final TextView server_address = (TextView) view.findViewById(R.id.server_address);

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
		list.add((Button) view.findViewById(R.id.letterQ));
		list.add((Button) view.findViewById(R.id.letterW));
		list.add((Button) view.findViewById(R.id.letterE));
		list.add((Button) view.findViewById(R.id.letterR));
		list.add((Button) view.findViewById(R.id.letterT));
		list.add((Button) view.findViewById(R.id.letterY));
		list.add((Button) view.findViewById(R.id.letterU));
		list.add((Button) view.findViewById(R.id.letterI));
		list.add((Button) view.findViewById(R.id.letterO));
		list.add((Button) view.findViewById(R.id.letterP));
		list.add((Button) view.findViewById(R.id.letterA));
		list.add((Button) view.findViewById(R.id.letterS));
		list.add((Button) view.findViewById(R.id.letterD));
		list.add((Button) view.findViewById(R.id.letterF));
		list.add((Button) view.findViewById(R.id.letterG));
		list.add((Button) view.findViewById(R.id.letterH));
		list.add((Button) view.findViewById(R.id.letterJ));
		list.add((Button) view.findViewById(R.id.letterK));
		list.add((Button) view.findViewById(R.id.letterL));
		list.add((Button) view.findViewById(R.id.letterColon));
		list.add((Button) view.findViewById(R.id.letterZ));
		list.add((Button) view.findViewById(R.id.letterX));
		list.add((Button) view.findViewById(R.id.letterC));
		list.add((Button) view.findViewById(R.id.letterV));
		list.add((Button) view.findViewById(R.id.letterB));
		list.add((Button) view.findViewById(R.id.letterN));
		list.add((Button) view.findViewById(R.id.letterM));
		list.add((Button) view.findViewById(R.id.letterDot));
		list.add((Button) view.findViewById(R.id.letterSlash));
		list.add((Button) view.findViewById(R.id.http));
		list.add((Button) view.findViewById(R.id.com));
		list.add((Button) view.findViewById(R.id.www));

		list.add((Button) view.findViewById(R.id.s192));
		list.add((Button) view.findViewById(R.id.s168));
		list.add((Button) view.findViewById(R.id.s101));
		list.add((Button) view.findViewById(R.id.sport));
		list.add((Button) view.findViewById(R.id.sappname));

		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Button button = (Button) v;
				server_address.append(button.getText());
			}
		};
		for (Button button : list) {
			button.setOnClickListener(listener);
		}

		Button backspace = (Button) view.findViewById(R.id.backspace);
		backspace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String text = server_address.getText().toString();
				text = back(text);
				server_address.setText(text);
			}

			private String back(String str) {
				if (StringUtils.isNotEmpty(str)) {
					return str.substring(0, str.length() - 1);
				}
				return "";
			}
		});

		Button clear = (Button) view.findViewById(R.id.clear);
		clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				server_address.setText(null);
			}

		});

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				String address = server_address.getText().toString();
				server_address.setText("");

				DodoroContext.getInstance().setServerAddress(parent, address);

				if (callback != null) {
					callback.callback();
				}
			}
		});

		builder.setCancelable(cancelable);

	}

	public void show() {
		builder.create().show();
	}

}
