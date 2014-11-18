package com.betalife.sushibuffet.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.dao.DiningtableMapper;
import com.betalife.sushibuffet.dao.OrderMapper;
import com.betalife.sushibuffet.dao.ProductMapper;
import com.betalife.sushibuffet.dao.TurnoverMapper;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Turnover;

@Service
public class CustomerManager {
	@Autowired
	private DiningtableMapper tableMapper;

	@Autowired
	private TurnoverMapper turnoverMapper;

	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private OrderMapper orderMapper;

	@Transactional
	public void openTable(Turnover turnover) {
		turnover.setFirstTableId(turnover.getTableId());
		turnoverMapper.insertTurnover(turnover);
	}

	public List<Category> getCategoriesByParentId(Category category) {
		return categoryMapper.selectByParentId(category);
	}

	public List<Diningtable> getTables() {
		return tableMapper.selectTables();
	}

	public List<Product> getProductsByCategoryId(Product product) {
		return productMapper.selectByCategoryId(product);
	}

	@Transactional
	public boolean takeOrders(List<Order> orders) {
		for (Order o : orders) {
			orderMapper.insertOrder(o);
		}
		return print(orders);
	}

	public List<Order> getOrders(Order order) {
		return orderMapper.selectOrders(order);
	}

	@Transactional
	public void checkout(int id) {
		turnoverMapper.checkout(id);
	}

	@Transactional
	public void changeTable(Turnover t) {
		turnoverMapper.changeTable(t);
	}

	public boolean printOrders(Order model) {
		List<Order> orders = getOrders(model);
		return print(orders);
	}

	private boolean print(List<Order> orders) {
		// TODO Auto-generated method stub
		return true;
	}

}
