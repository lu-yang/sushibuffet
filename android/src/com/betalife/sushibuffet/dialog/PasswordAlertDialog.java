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

@Deprecated
public class PasswordAlertDialog {

	private AlertDialog.Builder builder;

	public PasswordAlertDialog(final Activity parent, final Callback callback) {

		LayoutInflater layoutInflater = parent.getLayoutInflater();
		final View view = layoutInflater.inflate(R.layout.dialog_password, null);

		final TextView password = (TextView) view.findViewById(R.id.password);

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
				password.append("*");
				String tag = (String) password.getTag();
				tag = StringUtils.isEmpty(tag) ? "" : tag;
				tag += button.getText();
				password.setTag(tag);
			}
		};
		for (Button button : list) {
			button.setOnClickListener(listener);
		}

		Button backspace = (Button) view.findViewById(R.id.backspace);
		backspace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String tag = (String) password.getTag();
				tag = back(tag);
				password.setTag(tag);
				String text = password.getText().toString();
				text = back(text);
				password.setText(text);
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
				password.setTag("");
				password.setText(null);
			}

		});

		builder = new AlertDialog.Builder(parent);
		builder.setView(view);

		builder.setPositiveButton(R.string._ok, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				String tag = (String) password.getTag();
				TextView mes = (TextView) view.findViewById(R.id.mes_password_wrong);
				password.setText("");
				password.setTag(null);
				String pass = DodoroContext.getInstance().getConstant().getPassword();
				if (StringUtils.equals(tag, pass)) {
					mes.setVisibility(View.INVISIBLE);
					callback.callback();
				} else {
					mes.setVisibility(View.VISIBLE);
				}

			}
		});

		builder.setCancelable(true);

	}

	public void show() {
		builder.create().show();
	}

}
