package com.betalife.sushibuffet.model;

public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// private int turnoverId;
	// private int productId;
	private int count;

	private Product product;
	private Turnover turnover;

	private String locale;

	// public int getTurnoverId() {
	// return turnoverId;
	// }
	//
	// public void setTurnoverId(int turnoverId) {
	// this.turnoverId = turnoverId;
	// }
	//
	// public int getProductId() {
	// return productId;
	// }
	//
	// public void setProductId(int productId) {
	// this.productId = productId;
	// }

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
				+ count + ", id=" + id + "]" + ", locale=" + locale;
	}

}
