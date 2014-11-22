package com.betalife.sushibuffet.activity;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentSetting extends Fragment {

	public FragmentSetting() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_setting, container, false);

		final TextView password = (TextView) view.findViewById(R.id.password);
		Button passwordConfirmButton = (Button) view.findViewById(R.id.passwordConfirmButton);
		passwordConfirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String tag = (String) password.getTag();
				TextView mes = (TextView) view.findViewById(R.id.mes_password_wrong);
				password.setText("");
				password.setTag(null);
				String pass = DodoroContext.getInstance().getConstant().getPassword();
				if (StringUtils.equals(tag, pass)) {
					mes.setVisibility(View.INVISIBLE);
					Intent intent = new Intent();
					intent.setClass(FragmentSetting.this.getActivity(), SettingActivity.class);
					startActivity(intent);
				} else {
					mes.setVisibility(View.VISIBLE);
				}
			}
		});

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

		return view;
	}
}
