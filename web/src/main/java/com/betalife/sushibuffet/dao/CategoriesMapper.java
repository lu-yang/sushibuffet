package com.betalife.sushibuffet.dao;

import java.util.List;

import com.betalife.sushibuffet.model.Categories;

public interface CategoriesMapper {
	List<Categories> selectByParentId(int parentId);

	List<Categories> selectAll();
}
