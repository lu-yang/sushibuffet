package com.betalife.sushibuffet.model;

public class Diningtable extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean available;
	private Turnover turnover;

	public Turnover getTurnover() {
		return turnover;
	}

	public void setTurnover(Turnover turnover) {
		this.turnover = turnover;
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
