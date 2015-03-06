package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment implements Refreshable {

	protected int layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layout, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getActivity() instanceof MainActivity) {
			MainActivity activity = (MainActivity) getActivity();
			if (activity.getAdapter().isSelected(getClass())) {
				refresh();
			}
		}
	}

	@Override
	public void refresh() {
	}

}
