//package com.betalife.sushibuffet.util;
//
//import java.io.File;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.text.MessageFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.apache.commons.io.FileUtils;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.util.CollectionUtils;
//
//import com.betalife.sushibuffet.dao.TaxgroupsMapper;
//import com.betalife.sushibuffet.model.Category;
//import com.betalife.sushibuffet.model.Order;
//import com.betalife.sushibuffet.model.Product;
//import com.betalife.sushibuffet.model.Taxgroups;
//
//@Component
//public class ReceiptTempleteUtil extends TempleteUtil {
//
//	private static final String FOOD = "food";
//	private static final String FOOD_WRAPPER = "{" + FOOD + "}";
//	private static final String ALCOHOL = "alcool";
//	private static final String ALCOHOL_WRAPPER = "{" + ALCOHOL + "}";
//	private static final BigDecimal HUNDRED = new BigDecimal(100);
//	private static final BigDecimal ONE = new BigDecimal(1);
//	@Value("${order.template}")
//	public String order_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\OrderTemplate.txt";
//	@Value("${receipt.template}")
//	public String receipt_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\ReceiptTemplate.txt";
//
//	@Autowired
//	private TaxgroupsMapper taxgroupsMapper;
//
//	private final static String ESC_STR = "\\u001b";
//	private final static String ESC = "\u001b";
//
//	private final static String NL_STR = "\\n";
//	private final static String NL = "\n";
//
//	private List<String> receipt_lines = new LinkedList<String>();
//	private String receipt_pattern;
//	private static final Logger logger = LoggerFactory.getLogger(ReceiptTempleteUtil.class);
//
//	public static void main(String[] args) throws IOException {
//
//		List<Order> orders = new ArrayList<Order>();
//		Order o = new Order();
//		o.setCount(1);
//		Product product = new Product();
//		product.setProductName("shui");
//		product.setProductPrice(2322);
//		o.setProduct(product);
//		orders.add(o);
//		orders.add(o);
//		orders.add(o);
//
//		ReceiptTempleteUtil name = new ReceiptTempleteUtil();
//		name.init();
//		// System.out.println(name.format(orders));
//	}
//
//	public ReceiptTempleteUtil() throws IOException {
//		// init();
//	}
//
//	@PostConstruct
//	public void init() throws IOException {
//		init_receipt_lines();
//	}
//
//	private void init_receipt_lines() throws IOException {
//		List<String> lines = readLines(receipt_template);
//		for (String line : lines) {
//			if (StringUtils.isEmpty(line) || line.startsWith("#")) {
//				continue;
//			}
//
//			line = line.replace(ESC_STR, ESC);
//			line = line.replace(NL_STR, NL);
//
//			if (line.startsWith("$Loop ")) {
//				receipt_pattern = line.replace("$Loop ", "");
//			}
//
//			receipt_lines.add(line);
//		}
//	}
//
//	private List<String> readLines(String location) throws IOException {
//		File file = getFile(location);
//		List<String> lines = FileUtils.readLines(file);
//		return lines;
//	}
//
//	public List<String> format_receipt_lines(List<Order> orders, String locale) {
//		ArrayList<String> list = new ArrayList<String>();
//		Map<Integer, Category> map = getCategoryMap(locale);
//		int total = 0;
//		int tableId = 0;
//		if (!CollectionUtils.isEmpty(orders)) {
//			tableId = orders.get(0).getTurnover().getTableId();
//		}
//		Map<String, Taxgroups> taxMap = getTaxMap();
//
//		Map<Integer, Integer> taxs = new HashMap<Integer, Integer>();
//		for (String line : receipt_lines) {
//			if (line.startsWith("$Loop ")) {
//				// {0}:product_name, {1}:product_price, {2}:product_count ,
//				// {3}:product_num, {4}:category_name
//				if (!CollectionUtils.isEmpty(orders)) {
//					for (Order order : orders) {
//						Product product = order.getProduct();
//						Category category = map.get(product.getCategoryId());
//						String cateName = category == null ? "" : category.getName();
//						Object[] args = { product.getProductName(),
//								DodoroUtil.getDisplayPrice(product.getProductPrice()), order.getCount(),
//								product.getProductNum(), cateName };
//						String formated = MessageFormat.format(receipt_pattern, args);
//						list.add(formated);
//
//						int subTotal = product.getProductPrice() * order.getCount();
//						total += subTotal;
//						int taxgroupId = product.getTaxgroupId();
//						if (taxs.containsKey(taxgroupId)) {
//							Integer partTotal = taxs.get(taxgroupId);
//							taxs.put(taxgroupId, partTotal + subTotal);
//						} else {
//							taxs.put(taxgroupId, subTotal);
//						}
//					}
//				}
//
//				continue;
//			}
//
//			if (line.contains("{total}")) {
//				line = line.replace("{total}", DodoroUtil.getDisplayPrice(total));
//			}
//
//			if (line.contains("{tableNo}")) {
//				line = line.replace("{tableNo}", "" + tableId);
//			}
//
//			if (line.contains("{date}")) {
//				line = line.replace("{date}", sdf.format(new Date()));
//			}
//
//			String kind = null;
//			String wrapper = null;
//			if (line.contains(FOOD_WRAPPER)) {
//				kind = FOOD;
//				wrapper = FOOD_WRAPPER;
//				line = replaceTax(taxMap, kind, taxs, wrapper, line);
//			}
//
//			if (line.contains(ALCOHOL_WRAPPER)) {
//				kind = ALCOHOL;
//				wrapper = ALCOHOL_WRAPPER;
//				line = replaceTax(taxMap, kind, taxs, wrapper, line);
//			}
//
//			list.add(line);
//		}
//		logger.debug(list.toString());
//		return list;
//	}
//
//	private String replaceTax(Map<String, Taxgroups> taxMap, String kind, Map<Integer, Integer> taxs,
//			String wrapper, String line) {
//		Taxgroups taxgroups = taxMap.get(kind);
//		logger.debug("kind is " + kind);
//		int id = taxgroups.getId();
//		logger.debug("id is " + id);
//		logger.debug("taxs is " + taxs);
//		if (!taxs.containsKey(id)) {
//			line = line.replace(wrapper, "0");
//			return line;
//		}
//		Integer kindTax = taxs.get(id);
//		logger.debug("kindTax is " + kindTax);
//		BigDecimal partTotal = new BigDecimal(kindTax);
//		BigDecimal value = new BigDecimal(taxgroups.getValue());
//		BigDecimal tax = partTotal.multiply(value).divide((value.add(ONE)).multiply(HUNDRED), 2,
//				BigDecimal.ROUND_HALF_DOWN);
//		line = line.replace(wrapper, "" + tax.floatValue());
//		return line;
//	}
//
//	private Map<String, Taxgroups> getTaxMap() {
//		List<Taxgroups> taxs = taxgroupsMapper.selectAll();
//		Map<String, Taxgroups> taxMap = new HashMap<String, Taxgroups>();
//		for (Taxgroups taxgroups : taxs) {
//			taxMap.put(taxgroups.getName(), taxgroups);
//		}
//		return taxMap;
//	}
//
// }
