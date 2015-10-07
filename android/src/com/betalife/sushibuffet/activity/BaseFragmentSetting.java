package com.betalife.sushibuffet.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.betalife.sushibuffet.adapter.SettingFragmentButtonAdapter;
import com.betalife.sushibuffet.asynctask.CheckoutTask;
import com.betalife.sushibuffet.asynctask.PrintOrdersTask;
import com.betalife.sushibuffet.dialog.DiscountAlertDialog;
import com.betalife.sushibuffet.dialog.PasswordDialog;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public abstract class BaseFragmentSetting extends BaseFragment implements Callback {

	private TextView table_no;

	protected View.OnClickListener printOrdersClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			PrintOrdersTask task = new PrintOrdersTask(getActivity());
			task.execute();
		}
	};
	protected View.OnClickListener discountClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			DiscountAlertDialog dialog = new DiscountAlertDialog(getActivity(), null);
			dialog.show();
		}
	};
	protected View.OnClickListener checkoutClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle(R.string.setting_activity_checkout);
			dialog.setMessage(getString(R.string.setting_activity_checkout_msg));
			dialog.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					CheckoutTask task = new CheckoutTask(getActivity());
					Turnover turnover = DodoroContext.getInstance().getTurnover();
					turnover.setCheckout(true);
					task.execute(turnover);
				}
			});

			dialog.setNegativeButton(R.string._no, DodoroContext.noActionDialogClickListener);
			dialog.create().show();

		}
	};

	protected View.OnClickListener finishOrderClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
			dialog.setTitle(R.string.setting_activity_finishOrder);
			dialog.setMessage(getString(R.string.setting_activity_finishOrder_msg));
			dialog.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					DodoroContext.getInstance().setTurnover(null);

					// restart app
					DodoroContext.restartApp(getActivity());
				}
			});

			dialog.setNegativeButton(R.string._no, DodoroContext.noActionDialogClickListener);
			dialog.create().show();

		}
	};

	protected List<Pair<Integer, OnClickListener>> buttons;
	private PasswordDialog dialog;
	private SettingFragmentButtonAdapter adapter;

	public BaseFragmentSetting() {
		buttons = new ArrayList<Pair<Integer, OnClickListener>>();
		layout = R.layout.fragment_setting;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		table_no = (TextView) view.findViewById(R.id.identify);

		adapter = new SettingFragmentButtonAdapter(getActivity());
		addButton(printOrdersClickListener, R.string.setting_activity_printOrders);
		addButton(finishOrderClickListener, R.string.setting_activity_finishOrder);
		onCreateView();

		GridView buttonGridView = (GridView) view.findViewById(R.id.setting_fragment_buttons);
		buttonGridView.setAdapter(adapter);

		dialog = new PasswordDialog(getActivity(), this, true);

		return view;
	}

	private void setVisibility(int visibility) {
		adapter.setVisibility(visibility);
	}

	@Override
	public void callback() {
		setVisibility(View.VISIBLE);
		DodoroContext.getInstance().fillIdentify(getResources(), table_no);
	}

	@Override
	public void show() {
		setVisibility(View.INVISIBLE);
		dialog.show();
	}

	protected void addButton(OnClickListener onClickListener, int textId) {
		adapter.addItem(Pair.create(textId, onClickListener));
	}

	protected abstract void onCreateView();
}
