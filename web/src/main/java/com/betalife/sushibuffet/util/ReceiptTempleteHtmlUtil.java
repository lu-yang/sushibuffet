package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.dao.TaxgroupsMapper;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Taxgroups;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class ReceiptTempleteHtmlUtil extends TempleteUtil {

	@Value("${receipt.template}")
	public String receipt_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\ReceiptTemplate.txt";

	@Autowired
	private TaxgroupsMapper taxgroupsMapper;

	private final static String ESC_STR = "\\u001b";
	private final static String ESC = "\u001b";

	private final static String NL_STR = "\\n";
	private final static String NL = "\n";

	private static final String FOOD = "food";
	private static final String FOOD_WRAPPER = "{" + FOOD + "}";
	private static final String ALCOHOL = "alcool";
	private static final String ALCOHOL_WRAPPER = "{" + ALCOHOL + "}";
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private static final BigDecimal ONE = new BigDecimal(1);

	private static final Logger logger = LoggerFactory.getLogger(ReceiptTempleteHtmlUtil.class);

	private Template template;

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

		ReceiptTempleteHtmlUtil name = new ReceiptTempleteHtmlUtil();
		name.init();
		// System.out.println(name.format(orders));
	}

	@PostConstruct
	public void init() throws IOException {
		File file = getFile(receipt_template);
		String content = FileUtils.readFileToString(file);
		content = content.replace(ESC_STR, ESC);
		content = content.replace(NL_STR, NL);

		template = new Template(null, new StringReader(content), null);
		template.setEncoding("UTF-8");
	}

	public String format_receipt_lines(List<Order> orders, String locale) throws TemplateException,
			IOException {
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}

		Map<Integer, Category> categoryMap = getCategoryMap(locale);
		Map<Integer, Product> productMap = getProductMap(locale);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		int tableId = orders.get(0).getTurnover().getTableId();
		map.put("tableNo", tableId);

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int total = 0;
		Map<Integer, Integer> kindTotalMap = new HashMap<Integer, Integer>();

		for (Order order : orders) {
			Product product = order.getProduct();
			Category category = categoryMap.get(product.getCategoryId());
			String cateName = category == null ? "" : category.getName();

			Map<String, String> one = new HashMap<String, String>();
			one.put("pname", productMap.get(product.getId()).getProductName());
			int count = order.getCount();
			one.put("count", count + "");
			one.put("pnum", product.getProductNum());
			one.put("cname", cateName);
			int productPrice = product.getProductPrice();
			one.put("price", DodoroUtil.getDisplayPrice(productPrice));
			int subTotal = productPrice * count;
			one.put("subtotal", DodoroUtil.getDisplayPrice(subTotal));

			list.add(one);

			total += subTotal;
			int taxgroupId = product.getTaxgroupId();
			if (kindTotalMap.containsKey(taxgroupId)) {
				Integer kindTotal = kindTotalMap.get(taxgroupId);
				kindTotalMap.put(taxgroupId, kindTotal + subTotal);
			} else {
				kindTotalMap.put(taxgroupId, subTotal);
			}
		}

		map.put("list", list);

		map.put("total", DodoroUtil.getDisplayPrice(total));

		Map<String, Taxgroups> taxgroupsMap = getTaxgroupMap();

		putTotal(taxgroupsMap, FOOD, kindTotalMap, FOOD_WRAPPER, map);

		putTotal(taxgroupsMap, ALCOHOL, kindTotalMap, ALCOHOL_WRAPPER, map);

		StringWriter out = new StringWriter();
		template.process(map, out);
		String html = out.toString();
		logger.debug(html);

		return html;
	}

	private void putTotal(Map<String, Taxgroups> taxgroupsMap, String kind,
			Map<Integer, Integer> kindTotalMap, String wrapper, Map<String, Object> map) {
		Taxgroups taxgroups = taxgroupsMap.get(kind);
		int id = taxgroups.getId();
		BigDecimal kindTaxRate = new BigDecimal(taxgroups.getValue());
		if (!kindTotalMap.containsKey(id)) {
			map.put(kind + "_tax", "0");
			map.put(kind + "_total", "0");
			map.put(kind, "0");
		} else {
			BigDecimal kindTotal = new BigDecimal(kindTotalMap.get(id));
			BigDecimal kindTotalTax = kindTotal.multiply(kindTaxRate).divide(
					(kindTaxRate.add(ONE)).multiply(HUNDRED), 2, BigDecimal.ROUND_HALF_DOWN);
			map.put(kind + "_tax", kindTotalTax.floatValue());
			BigDecimal kindTotalF = kindTotal.divide(HUNDRED, 2, BigDecimal.ROUND_HALF_DOWN);
			map.put(kind + "_total", kindTotalF.floatValue());

			map.put(kind, "" + kindTotalF.subtract(kindTotalTax).floatValue());
		}
	}

	private Map<String, Taxgroups> getTaxgroupMap() {
		List<Taxgroups> taxgroups = taxgroupsMapper.selectAll();
		Map<String, Taxgroups> taxgroupMap = new HashMap<String, Taxgroups>();
		for (Taxgroups taxgroup : taxgroups) {
			taxgroupMap.put(taxgroup.getName(), taxgroup);
		}
		return taxgroupMap;
	}

}
