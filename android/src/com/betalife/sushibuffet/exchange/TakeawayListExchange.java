package com.betalife.sushibuffet.exchange;

import com.betalife.sushibuffet.model.Takeaway;

public class TakeawayListExchange extends BaseExchange {
	private Takeaway[] list;

	public Takeaway[] getList() {
		return list;
	}

	public void setList(Takeaway[] list) {
		this.list = list;
	}

}
