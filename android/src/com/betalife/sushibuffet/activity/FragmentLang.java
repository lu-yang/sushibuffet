package com.betalife.sushibuffet.activity;

import java.util.Locale;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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
				Resources resources = getResources();
				Configuration config = resources.getConfiguration();
				switch (v.getId()) {
				case R.id.lang_en:
					config.locale = Locale.ENGLISH;
					break;
				case R.id.lang_fr:
					config.locale = Locale.FRENCH;
					break;
				case R.id.lang_nl:
					config.locale = new Locale("nl");
					break;

				default:
					config.locale = new Locale("nl");
					break;
				}

				DisplayMetrics dm = resources.getDisplayMetrics();
				resources.updateConfiguration(config, dm);
				getActivity().recreate();
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
