package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Products;

public interface ProductsMapper {
	List<Products> selectByCategoryId(int categoryId);

	List<Products> selectAll();
}
