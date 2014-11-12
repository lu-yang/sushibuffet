package com.betalife.sushibuffet.model;

public class Constant {
	private String password;

	private int tables;

	private String categoryRootUrl;

	private String productRootUrl;

	public int getTables() {
		return tables;
	}

	public String getCategoryRootUrl() {
		return categoryRootUrl;
	}

	public void setCategoryRootUrl(String categoryRootUrl) {
		this.categoryRootUrl = categoryRootUrl;
	}

	public String getProductRootUrl() {
		return productRootUrl;
	}

	public void setProductRootUrl(String productRootUrl) {
		this.productRootUrl = productRootUrl;
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
