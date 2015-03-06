package com.betalife.sushibuffet.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		addButton(view, R.id.btn_discount, discountClickListener);
		addButton(view, R.id.btn_checkout, checkoutClickListener);
		addButton(view, R.id.btn_changeTable, changeTableClickListener);

		return view;
	}

}
