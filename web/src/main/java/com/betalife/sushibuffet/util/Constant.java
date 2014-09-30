package com.betalife.sushibuffet.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
	@Value("#{constants['waiter.password']}")
	private String password;
	@Value("#{constants['tables.count']}")
	private int tables;

	public int getTables() {
		return tables;
	}

	public void setTables(int tables) {
		this.tables = tables;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
