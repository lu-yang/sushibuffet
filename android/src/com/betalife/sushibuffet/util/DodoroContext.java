package com.betalife.sushibuffet.util;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.activity.R;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Turnover;

public class DodoroContext {
	private static DodoroContext instance;

	public static final Locale DEFAULT_LOCALE = new Locale("nl");

	private Constant constant;

	private Turnover turnover;

	private List<Order> currentOrdersCache;

	private String base_url;

	private Drawable noImage;

	private DodoroContext() {
	}

	public Drawable getNoImage() {
		return noImage;
	}

	public void setNoImage(Drawable noImage) {
		this.noImage = noImage;
	}

	public List<Order> getCurrentOrdersCache() {
		return currentOrdersCache;
	}

	public void setCurrentOrdersCache(List<Order> currentOrdersCache) {
		this.currentOrdersCache = currentOrdersCache;
	}

	public Turnover getTurnover() {
		return turnover;
	}

	public void setTurnover(Turnover turnover) {
		this.turnover = turnover;
	}

	public static DodoroContext getInstance() {
		if (instance == null) {
			instance = new DodoroContext();
		}
		return instance;
	}

	public Constant getConstant() {
		return constant;
	}

	public void setConstant(Constant constant) {
		this.constant = constant;
	}

	public static void locale(Locale locale, Activity activity) {
		Resources resources = activity.getResources();
		Configuration config = resources.getConfiguration();
		config.locale = locale;
		DisplayMetrics dm = resources.getDisplayMetrics();
		resources.updateConfiguration(config, dm);
		activity.recreate();

		Intent intent = new Intent();
		intent.setClass(activity, MainActivity.class);
		activity.startActivity(intent);
	}

	public static Locale locale(Activity activity) {
		Resources resources = activity.getResources();
		Configuration config = resources.getConfiguration();
		return config.locale;
	}

	public static String languageCode(Activity activity) {
		Resources resources = activity.getResources();
		Configuration config = resources.getConfiguration();
		return config.locale.getLanguage();
	}

	public static String getProductThumbUrl(String thumb) {
		if (StringUtils.isEmpty(thumb)) {
			thumb = instance.constant.getDefaultThumb();
		}
		return instance.constant.getProductRootUrl() + thumb;
	}

	public static String getCategoryThumbUrl(String thumb) {
		if (StringUtils.isEmpty(thumb)) {
			thumb = instance.constant.getDefaultThumb();
		}
		return instance.constant.getCategoryRootUrl() + thumb;
	}

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

	public static String getDiscountPrice(int total) {
		return getDisplayPrice(total / 100);
	}

	public static String getNum(Integer num) {
		if (num == null) {
			return "";
		} else if (num == 1) {
			return "/ P";
		} else if (num > 1) {
			return "/ " + num + " P";
		} else {
			return "";
		}
	}

	public static void restartApp(Activity activity) {
		Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		activity.startActivity(intent);
	}

	public void setServerAddress(Activity activity, String base_url) {
		this.base_url = base_url;
		SharedPreferences preferences = activity.getSharedPreferences(activity.getString(R.string.setting),
				Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("base_url", base_url);
		editor.commit();
	}

	public String getServerAddress(Activity activity) {
		if (StringUtils.isEmpty(base_url)) {
			SharedPreferences preferences = activity.getSharedPreferences(
					activity.getString(R.string.setting), Activity.MODE_PRIVATE);
			base_url = preferences.getString("base_url", null);
		}
		return base_url;
	}

}