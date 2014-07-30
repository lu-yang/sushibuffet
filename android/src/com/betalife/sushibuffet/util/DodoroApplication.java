package com.betalife.sushibuffet.util;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Application;

import com.betalife.sushibuffet.activity.R;

public class DodoroApplication extends Application {
	private DodoroApplication instance;

	private static Map<String, Locale> languageMap;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		createLanguageMap();
	}

	private void createLanguageMap() {
		languageMap = new HashMap<String, Locale>();
		languageMap.put(instance.getString(R.string.CHINESE), Locale.CHINESE);
		languageMap.put(instance.getString(R.string.JAPANESE), Locale.JAPANESE);
		languageMap.put(instance.getString(R.string.ENGLISH), Locale.ENGLISH);
	}

	public static Locale getLocale(String language) {
		return languageMap.get(language);
	}
}
