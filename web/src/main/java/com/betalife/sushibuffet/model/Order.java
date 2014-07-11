package com.betalife.sushibuffet.model;

public class Order extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int turnoverId;
	private int productId;
	private int count;
	private int roundNo;

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

	public int getRoundNo() {
		return roundNo;
	}

	public void setRoundNo(int roundNo) {
		this.roundNo = roundNo;
	}

	@Override
	public String toString() {
		return "Order [turnoverId=" + turnoverId + ", productId=" + productId + ", count=" + count
				+ ", roundNo=" + roundNo + ", id=" + id + "]";
	}

}
