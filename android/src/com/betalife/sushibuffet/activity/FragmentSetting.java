package com.betalife.sushibuffet.activity;

import android.view.View;

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

	protected void onCreateView() {
		addButton(discountClickListener, R.string.setting_activity_changeDiscount);
		addButton(checkoutClickListener, R.string.setting_activity_checkout);
		addButton(changeTableClickListener, R.string.setting_activity_change_table);
	}

}
