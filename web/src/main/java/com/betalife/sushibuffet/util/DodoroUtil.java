package com.betalife.sushibuffet.util;

public class DodoroUtil {
	public static String getDisplayPrice(int productPrice) {
		String price = null;
		if (productPrice < 10 && productPrice >= 0) {
			price = "0.0" + productPrice;
		} else if (productPrice < 100 && productPrice >= 10) {
			price = "0." + productPrice;
		} else if (productPrice >= 100) {
			String s = "" + productPrice;
			String last2 = s.substring(s.length() - 2);
			price = s.substring(0, s.length() - 2) + "." + last2;
		} else {
			price = "-";
		}

		return price;
	}
}
