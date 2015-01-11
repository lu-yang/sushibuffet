package com.betalife.sushibuffet.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import com.betalife.sushibuffet.dao.CategoryMapper;
import com.betalife.sushibuffet.dao.ProductMapper;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Product;

public abstract class TempleteUtil {
	@Autowired
	private CategoryMapper categoryMapper;

	@Autowired
	private ProductMapper productMapper;

	protected SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH);

	protected File getFile(String name) throws IOException {
		File file;
		if (name.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
			ClassPathResource resource = new ClassPathResource(
					name.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length()));
			file = resource.getFile();
		} else {
			file = new File(name);
		}
		return file;
	}

	protected Map<Integer, Category> getCategoryMap(String locale) {
		Map<Integer, Category> categories = null;
		List<Category> list = categoryMapper.selectAll(locale);
		categories = new HashMap<Integer, Category>();
		for (Category category : list) {
			categories.put(category.getId(), category);
		}

		return categories;
	}

	protected Map<Integer, Product> getProductMap(String locale) {
		Map<Integer, Product> products = null;
		List<Product> list = productMapper.selectAll(locale);
		products = new HashMap<Integer, Product>();
		for (Product one : list) {
			products.put(one.getId(), one);
		}

		return products;
	}
}
