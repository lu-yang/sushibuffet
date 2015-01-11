package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class OrderTempleteHtmlUtil extends TempleteUtil {

	@Value("${order.template}")
	public String order_template = "D:\\work\\shintech\\eclipse-jee-kepler-SR2-win32-x86_64-workspace\\sushibuffet\\web\\src\\main\\resources\\OrderTemplate.txt";

	private static final Logger logger = LoggerFactory.getLogger(OrderTempleteHtmlUtil.class);

	private Template template;

	private Html2ImageBytes html2ImageBytes = new Html2ImageBytes();

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

		OrderTempleteHtmlUtil name = new OrderTempleteHtmlUtil();
		name.init();
		// System.out.println(name.format(orders));
	}

	@PostConstruct
	public void init() throws IOException {
		File file = getFile(order_template);
		template = new Template(null, new InputStreamReader(new FileInputStream(file), "UTF-8"), null);
		template.setEncoding("UTF-8");
	}

	public byte[] format_order_lines(List<Order> orders, String locale) throws TemplateException, IOException {
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}

		ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<Integer, Category> categoryMap = getCategoryMap(locale);
		Map<Integer, Product> productMap = getProductMap(locale);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		int tableId = orders.get(0).getTurnover().getTableId();
		map.put("tableNo", tableId);

		for (Order order : orders) {
			Product product = order.getProduct();
			Category category = categoryMap.get(product.getCategoryId());
			String cateName = category == null ? "" : category.getName();

			Map<String, String> one = new HashMap<String, String>();
			one.put("pname", productMap.get(product.getId()).getProductName());
			one.put("count", order.getCount() + "");
			one.put("pnum", product.getProductNum());
			one.put("cname", cateName);
			list.add(one);
		}

		map.put("list", list);

		StringWriter out = new StringWriter();
		template.process(map, out);
		String html = out.toString();
		html2ImageBytes.loadHtml(html);
		logger.debug(html);
		byte[] bytes = html2ImageBytes.getBytes();
		return bytes;
	}

}
