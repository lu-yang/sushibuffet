package com.betalife.sushibuffet.util;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.betalife.sushibuffet.activity.R;

public class FragmentFactory {
	public static Fragment create(int index, Activity activity) {
		String[] fragments = activity.getResources().getStringArray(R.array.fragments);
		Fragment fragment = Fragment.instantiate(activity, fragments[index]);
		return fragment;
	}
}
