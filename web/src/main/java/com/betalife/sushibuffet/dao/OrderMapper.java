package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Order;

public interface OrderMapper {
	List<Order> selectByRoundNo(int roundNo);

	List<Order> selectByTurnoverId(int turnoverId);

	void insertOrder(Order o);
}
