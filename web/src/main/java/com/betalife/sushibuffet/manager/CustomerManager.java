package com.betalife.sushibuffet.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
import com.betalife.sushibuffet.util.Printer;
import com.betalife.sushibuffet.util.ReceiptTempleteUtil;

@Service
public class CustomerManager {

	private static final Logger logger = LoggerFactory.getLogger(CustomerManager.class);

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

	@Resource(name = "printer")
	private Printer printer;

	@Autowired
	private ReceiptTempleteUtil receiptTempleteUtil;

	@Value("${order.locale}")
	private String locale;

	@Value("${print.times}")
	private int times;

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
		if (CollectionUtils.isEmpty(orders)) {
			return true;
		}

		for (Order o : orders) {
			orderMapper.insertOrder(o);
		}

		List<String> list = receiptTempleteUtil.format_order_lines(orders, locale);
		return print(list, times);
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

	public boolean printOrders(Order model, boolean Kitchen) {
		List<Order> orders = getOrders(model);
		if (CollectionUtils.isEmpty(orders)) {
			return true;
		}
		Map<Integer, Order> map = new HashMap<Integer, Order>();
		for (Order order : orders) {
			int id = order.getProduct().getId();
			if (map.containsKey(id)) {
				Order one = map.get(id);
				one.setCount(one.getCount() + order.getCount());
			} else {
				Order one = order.copy();
				map.put(id, one);
			}
		}
		Collection<Order> values = map.values();
		List<String> list = null;
		if (Kitchen) {
			list = receiptTempleteUtil.format_order_lines(orders, locale);
		} else {
			list = receiptTempleteUtil.format_receipt_lines(new ArrayList<Order>(values), model.getLocale());
		}

		return print(list, times);
	}

	private boolean print(List<String> list, int times) {
		List<String> all = new ArrayList<String>((list.size() + 1) * times);
		for (int i = 0; i < times; i++) {
			all.addAll(list);
			printer.addCutPaper(all);
		}
		return print(all);
	}

	synchronized private boolean print(List<String> list) {
		try {
			printer.print(list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

}
