package com.betalife.sushibuffet.dialog;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.betalife.sushibuffet.activity.Callback;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.util.DodoroContext;

public class PasswordDialog extends Dialog {

	private Callback callback;

	public PasswordDialog(Activity parent, Callback callback, boolean cancelable) {
		super(parent);
		this.callback = callback;
		setCancelable(cancelable);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.dialog_password);

		final TextView password = (TextView) findViewById(R.id.password);

		List<Button> list = new LinkedList<Button>();
		list.add((Button) findViewById(R.id.one));
		list.add((Button) findViewById(R.id.two));
		list.add((Button) findViewById(R.id.three));
		list.add((Button) findViewById(R.id.four));
		list.add((Button) findViewById(R.id.five));
		list.add((Button) findViewById(R.id.six));
		list.add((Button) findViewById(R.id.seven));
		list.add((Button) findViewById(R.id.eight));
		list.add((Button) findViewById(R.id.nine));
		list.add((Button) findViewById(R.id.zero));

		View.OnClickListener listener = new View.OnClickListener() {

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

		Button backspace = (Button) findViewById(R.id.backspace);
		backspace.setOnClickListener(new View.OnClickListener() {
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

		Button clear = (Button) findViewById(R.id.clear);
		clear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				password.setTag("");
				password.setText(null);
			}

		});

		Button ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String tag = (String) password.getTag();
				TextView mes = (TextView) findViewById(R.id.mes_password_wrong);
				password.setText("");
				password.setTag(null);
				String pass = DodoroContext.getInstance().getConstant().getPassword();
				if (StringUtils.equals(tag, pass)) {
					mes.setVisibility(View.INVISIBLE);
					callback.callback();
					cancel();
				} else {
					mes.setVisibility(View.VISIBLE);
				}
			}
		});
	}
}
