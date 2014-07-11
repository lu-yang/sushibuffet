package com.betalife.sushibuffet.model;

public class Turnovers extends BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tableId;
	private boolean check;
	private int customerCount;
	private int roundNo;

	public int getTableId() {
		return tableId;
	}

	public void setTableId(int tableId) {
		this.tableId = tableId;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public int getCustomerCount() {
		return customerCount;
	}

	public void setCustomerCount(int customerCount) {
		this.customerCount = customerCount;
	}

	public int getRoundNo() {
		return roundNo;
	}

	public void setRoundNo(int roundNo) {
		this.roundNo = roundNo;
	}

	@Override
	public String toString() {
		return "Turnovers [tableId=" + tableId + ", check=" + check + ", customerCount=" + customerCount
				+ ", roundNo=" + roundNo + ", id=" + id + "]";
	}

}
