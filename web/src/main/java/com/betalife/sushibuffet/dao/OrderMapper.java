package com.betalife.sushibuffet.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.betalife.sushibuffet.model.Order;

public interface OrderMapper {

	List<Order> selectOrders(Order o);

	void insertOrder(Order o);

	List<Order> selectOrdersByDate(Map<String, Date> map);

	void delete(Order o);
}
