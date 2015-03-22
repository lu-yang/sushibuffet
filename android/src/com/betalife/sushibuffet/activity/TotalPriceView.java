package com.betalife.sushibuffet.activity;

import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import com.betalife.sushibuffet.util.DodoroContext;

public class TotalPriceView {

	private TextView discountTextView;
	private TextView totalPriceTextView;
	private String LBL_TOTAL;
	private String LBL_EUR;
	private String LBL_DISCOUNT;
	private String LBL_DISCOUNT_NO;
	private String LBL_DISCOUNT_FREE;
	private String LBL_DISCOUNT_PRICE;
	private TextView discountPriceTextView;
	protected View orders;

	public TotalPriceView(View view) {
		Resources resources = view.getResources();
		LBL_TOTAL = resources.getString(R.string.lbl_total);
		LBL_EUR = resources.getString(R.string.lbl_eur);
		LBL_DISCOUNT = resources.getString(R.string.lbl_discount);
		LBL_DISCOUNT_NO = resources.getString(R.string.lbl_discount_no);
		LBL_DISCOUNT_FREE = resources.getString(R.string.lbl_discount_free);
		LBL_DISCOUNT_PRICE = resources.getString(R.string.lbl_discount_price);
		totalPriceTextView = (TextView) view.findViewById(R.id.totalPrice);
		discountTextView = (TextView) view.findViewById(R.id.discount);
		discountPriceTextView = (TextView) view.findViewById(R.id.discountPrice);
	}

	public void setTotalPrice(int total, Integer percent) {
		totalPriceTextView.setText(LBL_TOTAL + DodoroContext.getDisplayPrice(total) + LBL_EUR);

		String discountText = null;
		if (percent == null) {
			discountText = LBL_DISCOUNT_NO;
		} else if (percent == 0) {
			discountText = LBL_DISCOUNT_FREE;
		} else {
			discountText = "-" + percent + "%";
		}
		discountTextView.setText(LBL_DISCOUNT + discountText);

		discountPriceTextView.setText(LBL_DISCOUNT_PRICE + DodoroContext.getDiscountPrice(total, percent)
				+ LBL_EUR);
	}
}
