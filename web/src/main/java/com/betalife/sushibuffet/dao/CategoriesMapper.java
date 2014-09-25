package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Categories;

public interface CategoriesMapper {
	List<Categories> selectByParentId(Categories categories);

	List<Categories> selectAll(String locale);
}
