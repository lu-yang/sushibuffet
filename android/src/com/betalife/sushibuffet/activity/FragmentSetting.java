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
import com.betalife.sushibuffet.dialog.PasswordAlertDialog;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentSetting extends Fragment implements PasswordAlertDialogCallback, Refreshable {

	public FragmentSetting() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_setting, container, false);
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		// refresh();
	}

	@Override
	public void callback() {
		TextView table_no = (TextView) getActivity().findViewById(R.id.table_no);
		int tableId = DodoroContext.getInstance().getTurnover().getTableId();
		table_no.setText(getString(R.string.lbl_table_no) + tableId);

		Button changeTable = (Button) getActivity().findViewById(R.id.btn_changeTable);
		changeTable.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), ChangeTableActivity.class);
				startActivity(intent);
			}
		});

		Button printOrders = (Button) getActivity().findViewById(R.id.btn_printOrders);
		printOrders.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				PrintOrdersTask task = new PrintOrdersTask(getActivity());
				task.execute();
			}
		});

		Button checkout = (Button) getActivity().findViewById(R.id.btn_checkout);
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

		Button finishOrder = (Button) getActivity().findViewById(R.id.btn_finishOrder);
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
	}

	@Override
	public void refresh() {
		PasswordAlertDialog dialog = new PasswordAlertDialog(this.getActivity(), this);
		dialog.show();
	}

}
