package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betalife.sushibuffet.asynctask.OrdersAsyncTask;

public class FragmentHistory extends Fragment implements Refreshable {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_history, container, false);
	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	@Override
	public void refresh() {
		OrdersAsyncTask task = new OrdersAsyncTask(getActivity(), true);
		task.execute();
	}
}
