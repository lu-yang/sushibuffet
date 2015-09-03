package com.betalife.sushibuffet.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.DetailOrderAdapter;
import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

public class FragmentDetailHistory extends BaseFragmentHistory {

	private TextView round;

	public FragmentDetailHistory() {
		layout = R.layout.fragment_detail_history;

		callback = new AsyncTaskCallback<Order>() {
			@Override
			public void callback(List<Order> list) {
				// setTotalPrice(list);

				DetailOrderAdapter adapter = new DetailOrderAdapter(getActivity());
				adapter.setRawList(list);
				ExpandableListView expandableOrders = (ExpandableListView) orders;
				expandableOrders.setAdapter(adapter);
				int groupCount = adapter.getGroupCount();
				for (int i = 0; i < groupCount; i++) {
					expandableOrders.expandGroup(i);
				}
			}

		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		round = (TextView) view.findViewById(R.id.round);

		return view;
	}

	@Override
	public void refresh() {
		super.refresh();
		DodoroContext instance = DodoroContext.getInstance();
		instance.fillRound(getResources(), round);
	}
}
