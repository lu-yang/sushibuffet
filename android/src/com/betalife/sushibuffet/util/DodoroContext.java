package com.betalife.sushibuffet.util;

import java.util.Map;

public class DodoroContext {
	private static DodoroContext instance;

	private Map<String, Object> constants;

	private DodoroContext() {
	}

	public static DodoroContext getInstance() {
		if (instance == null) {
			instance = new DodoroContext();
		}
		return instance;
	}

	public String getString(String key) {
		return (String) constants.get(key);
	}

	public void setConstants(Map<String, Object> constants) {
		this.constants = constants;
	}

}