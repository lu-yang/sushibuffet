package com.betalife.sushibuffet.model;

public class Turnovers extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private boolean checkout;

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
		return "Turnovers [tableId=" + tableId + ", checkout=" + checkout + ", id=" + id + "]";
	}

}
