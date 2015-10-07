package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements Refreshable {

	protected int layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(layout, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println("onResume in " + getClass().getName());

		if (getActivity() instanceof MainActivity) {
			MainActivity activity = (MainActivity) getActivity();
			// if (activity.getAdapter().isSelected(getClass())) {
			if (activity.isOnCreate()) {
				activity.setOnCreate(false);
				refresh();
			}
		}
	}

	@Override
	public void refresh() {
		System.out.println("refresh() in " + getClass().getName());
		if (isShowing()) {
			show();
		}
	}

	abstract protected void show();

	protected boolean isShowing() {
		if (isAdded()) {
			MainActivity mainActivity = (MainActivity) getActivity();
			if (mainActivity.getAdapter().isSelected(this.getClass())) {
				return true;
			}
		}
		return false;
	}

}
