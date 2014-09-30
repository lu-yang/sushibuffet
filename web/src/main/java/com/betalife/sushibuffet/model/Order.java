package com.betalife.sushibuffet.model;

public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int turnoverId;
	private int productId;
	private int count;

	public int getTurnoverId() {
		return turnoverId;
	}

	public void setTurnoverId(int turnoverId) {
		this.turnoverId = turnoverId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Order [turnoverId=" + turnoverId + ", productId=" + productId + ", count=" + count + ", id="
				+ id + "]";
	}

}
