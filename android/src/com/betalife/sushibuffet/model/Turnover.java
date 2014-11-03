package com.betalife.sushibuffet.model;

public class Turnover extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private boolean checkout;
	private int firstTableId;

	public int getFirstTableId() {
		return firstTableId;
	}

	public void setFirstTableId(int firstTableId) {
		this.firstTableId = firstTableId;
	}

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	@Override
	public String toString() {
		return "id=" + id;
	}

}
