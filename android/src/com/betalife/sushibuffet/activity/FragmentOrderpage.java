package com.betalife.sushibuffet.activity;

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.betalife.sushibuffet.adapter.CategoryAdapter;
import com.betalife.sushibuffet.asynctask.GetCategoriesAsyncTask;
import com.betalife.sushibuffet.asynctask.GetProductsByCategoryIdAsyncTask;
import com.betalife.sushibuffet.dialog.CurrentOrdersDialog;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.util.DodoroContext;

/* 菜单，点餐页面 */
public class FragmentOrderpage extends BaseFragment {
	// private ListView currentOrders;

	private TextView round;
	private TextView roundOrderCount;
	private TextView table_no;
	private TextView countDownTimer;

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

		countDownTimer = (TextView) view.findViewById(R.id.countDownTimer);

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

				CurrentOrdersDialog dialog = new CurrentOrdersDialog(getActivity(), true);
				dialog.show();
			}
		});

		return view;

	}

	@Override
	public void show() {
		final DodoroContext instance = DodoroContext.getInstance();
		instance.fillRound(getResources(), round);
		instance.fillIdentify(getResources(), table_no);
		instance.fillRoundOrderCount(getResources(), roundOrderCount);

		if (instance.isInRoundInterval()) {
			countDownTimer.setVisibility(View.VISIBLE);
			long millisInFuture = DodoroContext.getInstance().getInRoundInterval();
			CountDownTimer timer = new CountDownTimer(millisInFuture, 1000) {

				@Override
				public void onFinish() {
					countDownTimer.setVisibility(View.INVISIBLE);
				}

				@Override
				public void onTick(long millisUntilFinished) {
					countDownTimer.setText(getString(R.string.lbl_round_interval_left,
							millisUntilFinished / 1000));
				}

			};

			timer.start();
		} else {
			countDownTimer.setVisibility(View.INVISIBLE);
		}

		List<Category> categories = instance.getCategories();
		if (CollectionUtils.isEmpty(categories)) {
			CallbackResult<List<Category>> callback = new CallbackResult<List<Category>>() {

				@Override
				public void callback(final List<Category> list) {
					instance.setCategories(list);
					showCategories(list);
					showProduct(list);
				}
			};

			GetCategoriesAsyncTask getCategoriesAsyncTask = new GetCategoriesAsyncTask(getActivity(),
					callback, true);
			getCategoriesAsyncTask.execute();
		} else {
			showCategories(categories);
			showProduct(categories);
		}

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

	protected void showProduct(List<Category> list) {
		Category selected = null;
		for (Category category : list) {
			if (category.getId() == 2) {
				selected = category;
				break;
			}
		}
		if (selected == null) {
			selected = list.get(0);
		}

		GetProductsByCategoryIdAsyncTask getProductsByCategoryIdAsyncTask = new GetProductsByCategoryIdAsyncTask(
				getActivity(), true);
		getProductsByCategoryIdAsyncTask.execute(selected);
	}

	private void showRoundOutAlert() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
		DodoroContext instance = DodoroContext.getInstance();
		Constant constant = instance.getConstant();
		// dialog.setTitle(getString(R.string.ttl_round_out,
		// constant.getRounds()));
		dialog.setMessage(getString(R.string.lbl_round_out, constant.getRounds()));
		dialog.setNegativeButton(R.string._ok, DodoroContext.noActionDialogClickListener);
		dialog.create().show();
	}

	public void showCategories(final List<Category> list) {
		final FragmentActivity activity = getActivity();
		CategoryAdapter aa = new CategoryAdapter(activity, list);
		ListView categories = (ListView) activity.findViewById(R.id.categories);
		categories.setAdapter(aa);
		categories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Category selected = list.get(position);

				GetProductsByCategoryIdAsyncTask task2 = new GetProductsByCategoryIdAsyncTask(activity);
				task2.execute(selected);
			}
		});

	}
}
