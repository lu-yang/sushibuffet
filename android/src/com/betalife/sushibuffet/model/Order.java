package com.betalife.sushibuffet.model;

public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int count;
	private String locale;
	private Product product;
	private Turnover turnover;

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public int getCount() {
		return count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Turnover getTurnover() {
		return turnover;
	}

	public void setTurnover(Turnover turnover) {
		this.turnover = turnover;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Order [turnoverId=" + turnover.getId() + ", productId=" + product.getId() + ", count="
				+ count + ", id=" + id + "]";
	}

}
