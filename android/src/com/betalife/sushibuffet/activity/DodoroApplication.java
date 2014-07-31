package com.betalife.sushibuffet.activity;

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
		languageMap.put(instance.getString(R.string.FRENCH), Locale.FRENCH);
		languageMap.put(instance.getString(R.string.GERMAN), Locale.GERMAN);
		languageMap.put(instance.getString(R.string.DUTCH), new Locale("nl"));
	}

	public static Locale getLocale(String language) {
		return languageMap.get(language);
	}
}
