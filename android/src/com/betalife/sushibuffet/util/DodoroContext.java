package com.betalife.sushibuffet.util;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.betalife.sushibuffet.activity.MainActivity;
import com.betalife.sushibuffet.model.Constant;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Turnover;

public class DodoroContext {
	private static DodoroContext instance;

	public static final Locale DEFAULT_LOCALE = new Locale("nl");

	private Constant constant;

	private Turnover turnover;

	private List<Order> currentOrdersCache;

	private DodoroContext() {
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
}