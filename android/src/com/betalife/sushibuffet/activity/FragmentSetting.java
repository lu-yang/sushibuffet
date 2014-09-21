package com.betalife.sushibuffet.activity;

import org.apache.commons.lang.StringUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
		// Inflate the layout for this fragment

		final View view = inflater.inflate(R.layout.fragment_setting, container, false);

		Button passwordConfirmButton = (Button) view.findViewById(R.id.passwordConfirmButton);
		final EditText userInput = (EditText) view.findViewById(R.id.password);
		passwordConfirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Editable text = userInput.getText();
				String password = text.toString();
				String pass = DodoroContext.getInstance().getString("password");
				if (StringUtils.equals(password, pass)) {
					Intent intent = new Intent();
					intent.setClass(FragmentSetting.this.getActivity(), SettingActivity.class);
					startActivity(intent);
				} else {
					TextView mes = (TextView) view.findViewById(R.id.mes_password_wrong);
					mes.setVisibility(View.VISIBLE);
				}
			}
		});
		return view;
	}

}
