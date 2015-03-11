package com.betalife.sushibuffet.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.betalife.sushibuffet.asynctask.RemoveTakeawayTask;
import com.betalife.sushibuffet.asynctask.UpdateTakeawayTask;
import com.betalife.sushibuffet.model.Takeaway;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

public class FragmentTakeawaySetting extends BaseFragmentSetting {

	private boolean checkout = false;

	private DialogInterface.OnClickListener takeawayConfirmClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			UpdateTakeawayTask task = new UpdateTakeawayTask(getActivity(), checkout);
			Takeaway takeaway = DodoroContext.getInstance().getTakeaway();
			takeaway.setTakeaway(true);
			task.execute(takeaway);
		}
	};

	protected View.OnClickListener takeawayClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

			Turnover turnover = DodoroContext.getInstance().getTurnover();
			if (turnover.isCheckout()) {
				// checkout already
				builder.setTitle(R.string.setting_activity_takeaway);
				builder.setMessage(getString(R.string.setting_activity_takeaway_msg));
				builder.setNegativeButton(R.string._no, DodoroContext.noActionDialogClickListener);
				checkout = false;
				builder.setPositiveButton(R.string._yes, takeawayConfirmClickListener);
			} else {
				// not checkout yet
				builder.setTitle(R.string.setting_activity_uncheckout);
				builder.setMessage(getString(R.string.setting_activity_uncheckout_msg));
				builder.setNegativeButton(R.string._no, DodoroContext.noActionDialogClickListener);
				checkout = true;
				builder.setPositiveButton(R.string._yes, takeawayConfirmClickListener);
			}

			builder.create().show();

		}
	};

	private DialogInterface.OnClickListener removeTakeawayConfirmClickListener = new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			RemoveTakeawayTask task = new RemoveTakeawayTask(getActivity());
			Takeaway takeaway = DodoroContext.getInstance().getTakeaway();
			task.execute(takeaway);
		}
	};

	protected View.OnClickListener removeTakeawayClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {

			Turnover turnover = DodoroContext.getInstance().getTurnover();
			if (turnover.isCheckout()) {
				return;
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			// not checkout yet
			builder.setTitle(R.string.setting_activity_remove_takeaway);
			builder.setMessage(getString(R.string.setting_activity_remove_takeaway_msg));
			builder.setNegativeButton(R.string._no, DodoroContext.noActionDialogClickListener);
			builder.setPositiveButton(R.string._yes, removeTakeawayConfirmClickListener);

			builder.create().show();

		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		Takeaway takeaway = DodoroContext.getInstance().getTakeaway();
		if (takeaway.isTakeaway()) {
			return view;
		}
		Turnover turnover = takeaway.getTurnover();
		if (!turnover.isCheckout()) {
			addButton(view, R.id.btn_discount, discountClickListener);
			addButton(view, R.id.btn_checkout, checkoutClickListener);
			addButton(view, R.id.btn_remove_takeaway, removeTakeawayClickListener);
		}
		addButton(view, R.id.btn_takeaway, takeawayClickListener);

		return view;
	}

}
