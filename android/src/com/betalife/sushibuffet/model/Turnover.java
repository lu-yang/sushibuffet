package com.betalife.sushibuffet.model;

import java.util.Date;

public class Turnover extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private boolean checkout;
	private int firstTableId;
	private Integer discount;
	private Integer takeawayId;
	private Date created;
	private Date updated;
	private int round;
	private Date roundTime;
	private int roundOrderCount;

	public int getRoundOrderCount() {
		return roundOrderCount;
	}

	public void setRoundOrderCount(int roundOrderCount) {
		this.roundOrderCount = roundOrderCount;
	}

	public Date getRoundTime() {
		return roundTime;
	}

	public void setRoundTime(Date roundTime) {
		this.roundTime = roundTime;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Integer getTakeawayId() {
		return takeawayId;
	}

	public void setTakeawayId(Integer takeawayId) {
		this.takeawayId = takeawayId;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

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
		return "Turnover [tableId=" + tableId + ", checkout=" + checkout + ", id=" + id + ", firstTableId="
				+ firstTableId + ",discount=" + discount + ", takeawayId=" + takeawayId + ", created="
				+ created + ", updated" + updated + "]";

	}

	public void addRound() {
		++round;
	}
}
