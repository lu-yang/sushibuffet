package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;

@Component
public class ReceiptTempleteUtil {

	@Value("${receipt.template}")
	public String template;
	private List<String> readLines;
	private String pattern;

	public static void main(String[] args) throws IOException {

		ReceiptTempleteUtil name = new ReceiptTempleteUtil();
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
		System.out.println(name.format(orders));
	}

	public ReceiptTempleteUtil() throws IOException {
		// init();
	}

	@PostConstruct
	public void init() throws IOException {
		readLines = FileUtils.readLines(new File(template));
		for (String line : readLines) {
			if (StringUtils.isEmpty(line)) {
				continue;
			}
			if (line.startsWith("$Loop ")) {
				pattern = line.replace("$Loop ", "");
				break;
			}

		}
	}

	public List<String> format(List<Order> orders) {
		ArrayList<String> list = new ArrayList<String>();
		for (String line : readLines) {
			if (StringUtils.isEmpty(line) || line.startsWith("#")) {
				continue;
			}

			if (line.startsWith("$Loop ")) {
				for (Order order : orders) {
					Product product = order.getProduct();
					String formated = MessageFormat.format(pattern, product.getProductName(),
							DodoroUtil.getDisplayPrice(order.getProduct().getProductPrice()),
							order.getCount());
					list.add(formated);
				}
				continue;
			}

			list.add(line);
		}
		return list;
	}
}
