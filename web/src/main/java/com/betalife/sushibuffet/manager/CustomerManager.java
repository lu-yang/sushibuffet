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
import com.betalife.sushibuffet.util.OrderTempleteHtmlUtil;
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

	@Autowired
	private OrderTempleteHtmlUtil orderTempleteHtmlUtil;

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
	public boolean takeOrders(List<Order> orders) throws Exception {
		if (CollectionUtils.isEmpty(orders)) {
			return true;
		}

		for (Order o : orders) {
			orderMapper.insertOrder(o);
		}

		List<byte[]> imgs = orderTempleteHtmlUtil.format_order_lines(orders, locale);
		List<Object> list = new ArrayList<Object>();
		for (byte[] img : imgs) {
			list.add(img);
			list.add(printer.getCutPaper());
		}
		print(list, false, times);
		return true;
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

	public boolean printOrders(Order model, boolean kitchen) {
		List<Order> orders = getOrders(model);
		if (CollectionUtils.isEmpty(orders)) {
			logger.info("there is no order to print." + model);
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
		try {
			if (kitchen) {
				List<byte[]> imgs = orderTempleteHtmlUtil.format_order_lines(orders, locale);
				List<Object> list = new ArrayList<Object>();
				for (byte[] img : imgs) {
					list.add(img);
					list.add(printer.getCutPaper());
				}
				print(list, false, times);
			} else {
				List<String> list = receiptTempleteUtil.format_receipt_lines(new ArrayList<Order>(values),
						model.getLocale());
				list.add(printer.getCutPaper());
				print(list, true, times);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	synchronized private void print(List<?> list, boolean logo, int times) throws Exception {
		printer.print(list, logo, times);
	}

	// synchronized private void print(byte[] img, int times) throws Exception {
	// printer.print(img, times);
	// }

}
