package com.betalife.sushibuffet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.betalife.sushibuffet.asynctask.CheckoutTask;
import com.betalife.sushibuffet.asynctask.PrintOrdersTask;
import com.betalife.sushibuffet.dialog.PasswordDialog;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentSetting extends Fragment implements Callback, Refreshable {

	private Button changeTable;
	private Button printOrders;
	private Button checkout;
	private Button finishOrder;

	// private Button changeServerAddress;

	public FragmentSetting() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_setting, container, false);

		changeTable = (Button) view.findViewById(R.id.btn_changeTable);
		printOrders = (Button) view.findViewById(R.id.btn_printOrders);
		checkout = (Button) view.findViewById(R.id.btn_checkout);
		finishOrder = (Button) view.findViewById(R.id.btn_finishOrder);
		// changeServerAddress = (Button)
		// view.findViewById(R.id.btn_changeServerAddress);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		TextView table_no = (TextView) getActivity().findViewById(R.id.table_no);
		int tableId = DodoroContext.getInstance().getTurnover().getTableId();
		table_no.setText(getString(R.string.lbl_table_no) + tableId);

		setVisibility(View.INVISIBLE);
		// refresh();
	}

	private void setVisibility(int visibility) {
		changeTable.setVisibility(visibility);
		printOrders.setVisibility(visibility);
		checkout.setVisibility(visibility);
		finishOrder.setVisibility(visibility);
	}

	@Override
	public void callback() {
		changeTable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ChangeTableActivity.class);
				startActivity(intent);
			}
		});

		printOrders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PrintOrdersTask task = new PrintOrdersTask(getActivity());
				task.execute();
			}
		});

		checkout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
				dialog.setTitle(R.string.setting_activity_checkout);
				dialog.setMessage(getString(R.string.setting_activity_checkout_msg));
				dialog.setPositiveButton(R.string._yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						CheckoutTask task = new CheckoutTask(getActivity());
						task.execute(DodoroContext.getInstance().getTurnover());
					}
				});

				dialog.setNegativeButton(R.string._no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.create().show();

			}
		});

		finishOrder.setOnClickListener(new View.OnClickListener() {

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

				dialog.setNegativeButton(R.string._no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				dialog.create().show();

			}
		});

		// changeServerAddress.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// ServerAddressAlertDialog dialog = new
		// ServerAddressAlertDialog(getActivity(), null, true);
		// dialog.show();
		// }
		// });

		setVisibility(View.VISIBLE);
	}

	@Override
	public void refresh() {
		setVisibility(View.INVISIBLE);
		PasswordDialog dialog = new PasswordDialog(this.getActivity(), this, true);
		dialog.show();
	}

}
