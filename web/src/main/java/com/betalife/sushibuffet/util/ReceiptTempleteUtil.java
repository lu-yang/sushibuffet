package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;

@Component
public class ReceiptTempleteUtil {

	@Value("${order.template}")
	public String order_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\ReceiptTemplate.txt";
	@Value("${receipt.template}")
	public String receipt_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\ReceiptTemplate.txt";

	@Autowired
	private CategoryMapper categoryMapper;

	private final static String ESC_STR = "\\u001b";
	private final static String ESC = "\u001b";

	private final static String NL_STR = "\\n";
	private final static String NL = "\n";

	private List<String> order_lines = new LinkedList<String>();
	private List<String> receipt_lines = new LinkedList<String>();
	private String order_pattern;
	private String receipt_pattern;
	private static final Logger logger = LoggerFactory.getLogger(ReceiptTempleteUtil.class);

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

	public static void main(String[] args) throws IOException {

		List<Order> orders = new ArrayList<Order>();
		Order o = new Order();
		o.setCount(1);
		Product product = new Product();
		product.setProductName("shui");
		product.setProductPrice(2322);
		o.setProduct(product);
		orders.add(o);
		orders.add(o);
		orders.add(o);

		ReceiptTempleteUtil name = new ReceiptTempleteUtil();
		name.init();
		// System.out.println(name.format(orders));
	}

	public ReceiptTempleteUtil() throws IOException {
		// init();
	}

	@PostConstruct
	public void init() throws IOException {
		init_order_lines();
		init_receipt_lines();
	}

	private void init_receipt_lines() throws IOException {
		List<String> lines = FileUtils.readLines(new File(receipt_template));
		for (String line : lines) {
			if (StringUtils.isEmpty(line) || line.startsWith("#")) {
				continue;
			}

			line = line.replace(ESC_STR, ESC);
			line = line.replace(NL_STR, NL);

			if (line.startsWith("$Loop ")) {
				receipt_pattern = line.replace("$Loop ", "");
			}

			receipt_lines.add(line);
		}
	}

	private void init_order_lines() throws IOException {
		List<String> lines = FileUtils.readLines(new File(order_template));
		for (String line : lines) {
			if (StringUtils.isEmpty(line) || line.startsWith("#")) {
				continue;
			}

			line = line.replace(ESC_STR, ESC);
			line = line.replace(NL_STR, NL);

			if (line.startsWith("$Loop ")) {
				order_pattern = line.replace("$Loop ", "");
			}

			order_lines.add(line);
		}

	}

	public List<String> format_receipt_lines(List<Order> orders, String locale) {
		ArrayList<String> list = new ArrayList<String>();
		Map<Integer, Category> map = getCategoryMap(locale);
		int total = 0;
		int tableId = 0;
		if (!CollectionUtils.isEmpty(orders)) {
			tableId = orders.get(0).getTurnover().getTableId();
		}
		for (String line : receipt_lines) {
			if (line.startsWith("$Loop ")) {
				// {0}:product_name, {1}:product_price, {2}:product_count ,
				// {3}:product_num, {4}:category_name
				if (!CollectionUtils.isEmpty(orders)) {
					for (Order order : orders) {
						Product product = order.getProduct();
						Category category = map.get(product.getCategoryId());
						String cateName = category == null ? "" : category.getName();
						Object[] args = { product.getProductName(),
								DodoroUtil.getDisplayPrice(product.getProductPrice()), order.getCount(),
								product.getProductNum(), cateName };
						String formated = MessageFormat.format(receipt_pattern, args);
						list.add(formated);

						total += product.getProductPrice();
					}
				}

				continue;
			}

			if (line.contains("{total}")) {
				line = line.replace("{total}", DodoroUtil.getDisplayPrice(total));
			}

			if (line.contains("{tableNo}")) {
				line = line.replace("{tableNo}", "" + tableId);
			}

			if (line.contains("{date}")) {
				line = line.replace("{date}", sdf.format(new Date()));
			}

			list.add(line);
		}
		logger.debug(list.toString());
		return list;
	}

	public List<String> format_order_lines(List<Order> orders, String locale) {
		ArrayList<String> list = new ArrayList<String>();
		Map<Integer, Category> map = getCategoryMap(locale);
		int tableId = 0;
		if (!CollectionUtils.isEmpty(orders)) {
			tableId = orders.get(0).getTurnover().getTableId();
		}
		for (String line : order_lines) {
			if (line.startsWith("$Loop ")) {
				// {0}:product_name, {1}:product_price, {2}:product_count ,
				// {3}:product_num, {4}:category_name
				if (!CollectionUtils.isEmpty(orders)) {
					for (Order order : orders) {
						Product product = order.getProduct();
						Category category = map.get(product.getCategoryId());
						String cateName = category == null ? "" : category.getName();
						Object[] args = { product.getProductName(),
								DodoroUtil.getDisplayPrice(product.getProductPrice()), order.getCount(),
								product.getProductNum(), cateName };
						String formated = MessageFormat.format(order_pattern, args);
						list.add(formated);

					}
				}

				continue;
			}

			if (line.contains("{tableNo}")) {
				line = line.replace("{tableNo}", "" + tableId);
			}

			if (line.contains("{date}")) {
				line = line.replace("{date}", sdf.format(new Date()));
			}

			list.add(line);
		}
		logger.debug(list.toString());
		return list;
	}

	private Map<Integer, Category> getCategoryMap(String locale) {
		Map<Integer, Category> categories = null;
		List<Category> list = categoryMapper.selectAll(locale);
		categories = new HashMap<Integer, Category>();
		for (Category category : list) {
			categories.put(category.getId(), category);
		}

		return categories;
	}

}
