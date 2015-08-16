package com.betalife.sushibuffet.activity;

import java.util.LinkedList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.betalife.sushibuffet.asynctask.GetCategoriesAsyncTask;
import com.betalife.sushibuffet.asynctask.GetProductsByCategoryIdAsyncTask;
import com.betalife.sushibuffet.dialog.CurrentOrdersDialog;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

/* 菜单，点餐页面 */
public class FragmentOrderpage extends BaseFragment {
	// private ListView currentOrders;

	// private boolean init = true;
	public FragmentOrderpage() {
		super();
		layout = R.layout.fragment_orderpage;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkedList<Order> linkedList = new LinkedList<Order>();
		DodoroContext.getInstance().setCurrentOrdersCache(linkedList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		DodoroContext.getInstance().fillIdentify(getResources(), view);

		// currentOrders = (ListView) view.findViewById(R.id.current_orders);

		Button btn_take_orders = (Button) view.findViewById(R.id.btn_take_orders);
		btn_take_orders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CurrentOrdersDialog dialog = new CurrentOrdersDialog(getActivity());
				dialog.show();
			}
		});

		return view;

	}

	@Override
	public void refresh() {
		super.refresh();
		GetCategoriesAsyncTask getCategoriesAsyncTask = new GetCategoriesAsyncTask(getActivity(), true);
		getCategoriesAsyncTask.execute();

		GetProductsByCategoryIdAsyncTask getProductsByCategoryIdAsyncTask = new GetProductsByCategoryIdAsyncTask(
				getActivity(), true);
		getProductsByCategoryIdAsyncTask.execute(2);

		// final List<Order> currentOrdersCache =
		// DodoroContext.getInstance().getCurrentOrdersCache();
		// CurrentOrderAdapter oa = new CurrentOrderAdapter(getActivity(),
		// currentOrdersCache);
		// currentOrders.setAdapter(oa);
		// currentOrders.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id) {
		// Order selected = currentOrdersCache.get(position);
		//
		// OrderAlertDialog dialog = new OrderAlertDialog(getActivity(),
		// selected.getProduct());
		// dialog.show();
		// }
		// });

	}
}
