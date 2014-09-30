package com.betalife.sushibuffet.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betalife.sushibuffet.dao.CategoriesMapper;
import com.betalife.sushibuffet.dao.DiningtableMapper;
import com.betalife.sushibuffet.dao.ProductsMapper;
import com.betalife.sushibuffet.dao.TurnoversMapper;
import com.betalife.sushibuffet.model.Categories;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Products;
import com.betalife.sushibuffet.model.Turnovers;

@Service
public class CustomerManager {
	@Autowired
	private DiningtableMapper tableMapper;

	@Autowired
	private TurnoversMapper turnoversMapper;

	@Autowired
	private CategoriesMapper categoriesMapper;

	@Autowired
	private ProductsMapper productsMapper;

	@Transactional
	public void openTable(Turnovers turnovers) {
		turnoversMapper.insertTurnovers(turnovers);
	}

	public List<Categories> getCategoriesByParentId(Categories categories) {
		return categoriesMapper.selectByParentId(categories);
	}

	public List<Diningtable> getTables() {
		return tableMapper.selectTables();
	}

	public List<Products> getProductsByCategoryId(Products products) {
		return productsMapper.selectByCategoryId(products);
	}
}
