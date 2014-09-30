package com.betalife.sushibuffet.model;

public class Diningtable extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean available;
	private boolean checkout;

	public boolean isCheckout() {
		return checkout;
	}

	public void setCheckout(boolean checkout) {
		this.checkout = checkout;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String toString() {
		return "" + id;
	}

}
