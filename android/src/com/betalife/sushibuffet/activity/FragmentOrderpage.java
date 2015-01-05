package com.betalife.sushibuffet.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.CurrentOrderAdapter;
import com.betalife.sushibuffet.asynctask.GetCategoriesAsyncTask;
import com.betalife.sushibuffet.asynctask.GetProductsByCategoryIdAsyncTask;
import com.betalife.sushibuffet.asynctask.TakeOrdersAsyncTask;
import com.betalife.sushibuffet.dialog.OrderAlertDialog;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */

/* 菜单，点餐页面 */
public class FragmentOrderpage extends Fragment implements Refreshable {
	private boolean init = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinkedList<Order> linkedList = new LinkedList<Order>();
		DodoroContext.getInstance().setCurrentOrdersCache(linkedList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_orderpage, container, false);

		TextView table_no = (TextView) view.findViewById(R.id.table_no);
		int tableId = DodoroContext.getInstance().getTurnover().getTableId();
		table_no.setText(getActivity().getString(R.string.lbl_table_no) + tableId);

		Button btn_take_orders = (Button) view.findViewById(R.id.btn_take_orders);
		btn_take_orders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage(R.string.msg_take_orders);
				builder.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						TakeOrdersAsyncTask task = new TakeOrdersAsyncTask(getActivity());
						task.execute();
					}
				});

				builder.setNegativeButton(R.string._no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});

				builder.create().show();
			}
		});

		return view;

	}

	@Override
	public void onResume() {
		super.onResume();
		if (init) {
			init = false;
			refresh();
		}
	}

	@Override
	public void refresh() {

		GetCategoriesAsyncTask task = new GetCategoriesAsyncTask(getActivity(), true);
		task.execute();
		// TODO
		GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(getActivity(), 2, true);
		task2.execute();

		final List<Order> currentOrdersCache = DodoroContext.getInstance().getCurrentOrdersCache();
		CurrentOrderAdapter oa = new CurrentOrderAdapter(getActivity(), currentOrdersCache);
		ListView currentOrders = (ListView) getActivity().findViewById(R.id.current_orders);
		currentOrders.setAdapter(oa);
		currentOrders.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Order selected = currentOrdersCache.get(position);

				OrderAlertDialog dialog = new OrderAlertDialog(getActivity(), selected.getProduct());
				dialog.show();
			}
		});

	}
}
