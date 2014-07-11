package com.betalife.sushibuffet.model;

public class Diningtable extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean available;
	private int turnoverId;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getTurnoverId() {
		return turnoverId;
	}

	public void setTurnoverId(int turnoverId) {
		this.turnoverId = turnoverId;
	}

}
