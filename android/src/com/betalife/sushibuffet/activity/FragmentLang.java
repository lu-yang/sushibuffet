package com.betalife.sushibuffet.activity;

import java.util.Locale;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */

/* 语言选择 */
public class FragmentLang extends Fragment {

	public FragmentLang() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View langFragment = inflater.inflate(R.layout.fragment_lang, container, false);
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Locale locale = null;
				switch (v.getId()) {
				case R.id.lang_en:
					locale = Locale.ENGLISH;
					break;
				case R.id.lang_fr:
					locale = Locale.FRENCH;
					break;
				case R.id.lang_nl:
					locale = new Locale("nl");
					break;
				default:
					locale = DodoroContext.DEFAULT_LOCALE;
					break;
				}

				DodoroContext.locale(locale, getActivity());
			}
		};
		Button lang_en = (Button) langFragment.findViewById(R.id.lang_en);
		lang_en.setOnClickListener(clickListener);
		Button lang_fr = (Button) langFragment.findViewById(R.id.lang_fr);
		lang_fr.setOnClickListener(clickListener);
		Button lang_nl = (Button) langFragment.findViewById(R.id.lang_nl);
		lang_nl.setOnClickListener(clickListener);
		return langFragment;
	}

}
