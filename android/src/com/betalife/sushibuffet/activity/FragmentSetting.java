package com.betalife.sushibuffet.activity;

import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

import com.betalife.sushibuffet.asynctask.ChangeRoundOrderCountTask;
import com.betalife.sushibuffet.dialog.RoundOrderCountAlertDialog;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.DodoroContext;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class FragmentSetting extends BaseFragmentSetting {

	View.OnClickListener changeTableClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			DodoroContext.goTo(ChangeTableActivity.class, getActivity());
		}
	};

	protected View.OnClickListener changeRoundOrderCountClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			final Turnover turnover = DodoroContext.getInstance().getTurnover();
			final int tableId = turnover.getTableId();
			final FragmentActivity activity = getActivity();
			CallbackResult<Integer> callback = new CallbackResult<Integer>() {

				@Override
				public void callback(Integer value) {
					turnover.setRoundOrderCount(value);
					Toast.makeText(
							activity,
							activity.getString(R.string.msg_round_order_count, tableId,
									turnover.getRoundOrderCount()), Toast.LENGTH_SHORT).show();
					ChangeRoundOrderCountTask task = new ChangeRoundOrderCountTask(activity);
					task.execute(turnover);
				}
			};

			RoundOrderCountAlertDialog dialog = new RoundOrderCountAlertDialog(getActivity(), tableId,
					callback);
			dialog.show();
		}
	};

	@Override
	protected void onCreateView() {
		addButton(discountClickListener, R.string.setting_activity_changeDiscount);
		addButton(checkoutClickListener, R.string.setting_activity_checkout);
		addButton(changeTableClickListener, R.string.setting_activity_change_table);

		addButton(changeRoundOrderCountClickListener, R.string.setting_activity_change_round_order_count);
	}

}
