package com.betalife.sushibuffet.activity;

import java.util.List;

import android.widget.ExpandableListView;

import com.betalife.sushibuffet.adapter.DetailOrderAdapter;
import com.betalife.sushibuffet.asynctask.AsyncTaskCallback;
import com.betalife.sushibuffet.model.Order;

public class FragmentDetailHistory extends BaseFragmentHistory {

	public FragmentDetailHistory() {
		layout = R.layout.fragment_detail_history;

		callback = new AsyncTaskCallback<Order>() {
			@Override
			public void callback(List<Order> list) {
				setTotalPrice(list);

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

}
