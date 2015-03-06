package com.betalife.sushibuffet.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

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

	protected List<Button> buttons;

	public BaseFragmentSetting() {
		buttons = new ArrayList<Button>();
		layout = R.layout.fragment_setting;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		DodoroContext.getInstance().fillIdentify(getResources(), view);

		addButton(view, R.id.btn_printOrders, printOrdersClickListener);
		addButton(view, R.id.btn_finishOrder, finishOrderClickListener);

		return view;
	}

	private void setVisibility(int visibility) {
		for (Button one : buttons) {
			one.setVisibility(visibility);
		}
	}

	@Override
	public void callback() {
		setVisibility(View.VISIBLE);
	}

	@Override
	public void refresh() {
		setVisibility(View.INVISIBLE);
		PasswordDialog dialog = new PasswordDialog(this.getActivity(), this, true);
		dialog.show();
	}

	protected void addButton(View view, int resouceId, OnClickListener onClickListener) {
		Button button = (Button) view.findViewById(resouceId);
		button.setOnClickListener(onClickListener);
		buttons.add(button);
	}

}
