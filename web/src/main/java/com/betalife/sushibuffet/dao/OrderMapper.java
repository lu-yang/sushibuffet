package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Order;

public interface OrderMapper {

	List<Order> selectOrders(Order o);

	void insertOrder(Order o);
}
