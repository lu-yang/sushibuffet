package com.betalife.sushibuffet.activity;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.asynctask.GetCategoriesAsyncTask;
import com.betalife.sushibuffet.asynctask.GetProductsByCategoryIdAsyncTask;
import com.betalife.sushibuffet.dialog.CurrentOrdersDialog;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

/* 菜单，点餐页面 */
public class FragmentOrderpage extends BaseFragment {
	// private ListView currentOrders;

	private TextView round;
	private TextView roundOrderCount;
	private TextView table_no;

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

		table_no = (TextView) view.findViewById(R.id.identify);

		round = (TextView) view.findViewById(R.id.round);

		roundOrderCount = (TextView) view.findViewById(R.id.roundOrderCount);

		// currentOrders = (ListView) view.findViewById(R.id.current_orders);

		Button btn_take_orders = (Button) view.findViewById(R.id.btn_take_orders);
		btn_take_orders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				DodoroContext instance = DodoroContext.getInstance();
				if (instance.isOverRound()) {
					showRoundOutAlert();
					return;
				}

				List<Order> currentOrdersCache = instance.getCurrentOrdersCache();
				if (CollectionUtils.isEmpty(currentOrdersCache)) {
					Toast.makeText(getActivity(), R.string.lbl_no_order, Toast.LENGTH_SHORT).show();
					return;
				}

				if (instance.isInRoundInterval()) {
					Toast.makeText(
							getActivity(),
							getString(R.string.lbl_round_interval, instance.getConstant().getRoundInterval()),
							Toast.LENGTH_SHORT).show();
					return;
				}

				CurrentOrdersDialog dialog = new CurrentOrdersDialog(getActivity(), true);
				dialog.show();
			}
		});

		return view;

	}

	@Override
	public void refresh() {
		super.refresh();
		DodoroContext instance = DodoroContext.getInstance();
		instance.fillRound(getResources(), round);
		instance.fillIdentify(getResources(), table_no);
		instance.fillRoundOrderCount(getResources(), roundOrderCount);

		GetCategoriesAsyncTask getCategoriesAsyncTask = new GetCategoriesAsyncTask(getActivity(), true);
		getCategoriesAsyncTask.execute();

		GetProductsByCategoryIdAsyncTask getProductsByCategoryIdAsyncTask = new GetProductsByCategoryIdAsyncTask(
				getActivity(), true);
		getProductsByCategoryIdAsyncTask.execute(2);

		if (instance.isOverRound()) {
			showRoundOutAlert();
			return;
		}

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

	private void showRoundOutAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		DodoroContext instance = DodoroContext.getInstance();
		Constant constant = instance.getConstant();
		dialog.setTitle(getString(R.string.round_out, constant.getRounds()));
		dialog.setMessage(getString(R.string.round_out_msg, constant.getRounds()));
		dialog.setNegativeButton(R.string._ok, DodoroContext.noActionDialogClickListener);
		dialog.create().show();
	}
}
