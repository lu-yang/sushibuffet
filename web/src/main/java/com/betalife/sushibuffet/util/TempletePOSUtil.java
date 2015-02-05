package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.betalife.sushibuffet.dao.TaxgroupsMapper;
import com.betalife.sushibuffet.model.Taxgroups;

import freemarker.template.Template;

public abstract class TempletePOSUtil extends TempleteUtil {

	@Autowired
	private TaxgroupsMapper taxgroupsMapper;

	private final static String ESC_STR = "\\u001b";
	private final static String ESC = "\u001b";

	private final static String NL_STR = "\\n";
	private final static String NL = "\n";

	protected static final String FOOD = "food";
	protected static final String FOOD_WRAPPER = "{" + FOOD + "}";
	protected static final String ALCOHOL = "alcool";
	protected static final String ALCOHOL_WRAPPER = "{" + ALCOHOL + "}";
	private static final BigDecimal HUNDRED = new BigDecimal(100);
	private static final BigDecimal ONE = new BigDecimal(1);

	@PostConstruct
	public void init() throws IOException {
		File file = getFile();
		String content = FileUtils.readFileToString(file);
		content = content.replace(ESC_STR, ESC);
		content = content.replace(NL_STR, NL);

		template = new Template(null, new StringReader(content), null);
		template.setEncoding("UTF-8");
	}

	protected void putTotal(Map<String, Taxgroups> taxgroupsMap, String kind,
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

	protected Map<String, Taxgroups> getTaxgroupMap() {
		List<Taxgroups> taxgroups = taxgroupsMapper.selectAll();
		Map<String, Taxgroups> taxgroupMap = new HashMap<String, Taxgroups>();
		for (Taxgroups taxgroup : taxgroups) {
			taxgroupMap.put(taxgroup.getName(), taxgroup);
		}
		return taxgroupMap;
	}

}
