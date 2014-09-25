package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Products;

public interface ProductsMapper {
	List<Products> selectByCategoryId(Products product);

	List<Products> selectById(Products product);

	List<Products> selectAll(String locale);
}
