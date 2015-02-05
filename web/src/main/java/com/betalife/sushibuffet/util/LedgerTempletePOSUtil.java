package com.betalife.sushibuffet.util;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Taxgroups;

import freemarker.template.TemplateException;

@Component
public class LedgerTempletePOSUtil extends TempletePOSUtil {
	@Value("${ledger.template}")
	@Override
	protected void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public Map<String, Object> buildParam(List<Order> orders) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("date", sdf.format(new Date()));

		int total = 0;
		Map<Integer, Integer> kindTotalMap = new HashMap<Integer, Integer>();

		for (Order order : orders) {
			Product product = order.getProduct();
			int count = order.getCount();
			int productPrice = product.getProductPrice();
			int subTotal = productPrice * count;

			total += subTotal;
			int taxgroupId = product.getTaxgroupId();
			if (kindTotalMap.containsKey(taxgroupId)) {
				Integer kindTotal = kindTotalMap.get(taxgroupId);
				kindTotalMap.put(taxgroupId, kindTotal + subTotal);
			} else {
				kindTotalMap.put(taxgroupId, subTotal);
			}
		}

		map.put("total", DodoroUtil.getDisplayPrice(total));

		Map<String, Taxgroups> taxgroupsMap = getTaxgroupMap();

		putTotal(taxgroupsMap, FOOD, kindTotalMap, FOOD_WRAPPER, map);

		putTotal(taxgroupsMap, ALCOHOL, kindTotalMap, ALCOHOL_WRAPPER, map);

		return map;
	}

	public String format_receipt_lines(List<Order> orders) throws TemplateException, IOException {
		if (CollectionUtils.isEmpty(orders)) {
			return null;
		}
		Map<String, Object> map = buildParam(orders);
		String html = format(map);

		return html;
	}

}
