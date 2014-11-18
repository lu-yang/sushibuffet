/*
 * Copyright 2010-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betalife.sushibuffet.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betalife.sushibuffet.manager.CustomerManager;
import com.betalife.sushibuffet.model.Category;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Order;
import com.betalife.sushibuffet.model.Product;
import com.betalife.sushibuffet.model.Turnover;
import com.betalife.sushibuffet.util.Constant;

/**
 * Handles requests for the application home page.
 * 
 * @author Roy Clarkson
 */
@Controller
@RequestMapping("/*")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private CustomerManager customerManager;

	@Autowired
	private Constant constant;

	@RequestMapping(value = "availableTables", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Diningtable> fetchAllTables() {
		List<Diningtable> allTables = customerManager.getTables();
		return allTables;
	}

	@RequestMapping(value = "categories/{locale}/{parentId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Category> fetchRootCategories(@PathVariable String locale, @PathVariable int parentId) {
		Category model = new Category();
		model.setParentId(parentId);
		model.setLocale(locale);
		List<Category> all = customerManager.getCategoriesByParentId(model);
		return all;
	}

	@RequestMapping(value = "openTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	Turnover openTable(@RequestBody Turnover turnover) {
		customerManager.openTable(turnover);
		return turnover;
	}

	@RequestMapping(value = "constant", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Constant fetchConstants() {
		return constant;
	}

	@RequestMapping(value = "products/{locale}/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Product> fetchProductsByCategoryId(@PathVariable String locale, @PathVariable int categoryId) {
		Product model = new Product();
		model.setCategoryId(categoryId);
		model.setLocale(locale);
		List<Product> all = customerManager.getProductsByCategoryId(model);
		return all;
	}

	@RequestMapping(value = "takeOrders/{turnoverId}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	boolean takeOrders(@PathVariable int turnoverId, @RequestBody List<Order> orders) {
		return customerManager.takeOrders(orders);
	}

	@RequestMapping(value = "orders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	List<Order> orders(@PathVariable String locale, @PathVariable int turnoverId) {
		Order model = buildOrder(locale, turnoverId);
		List<Order> orders = customerManager.getOrders(model);
		return orders;
	}

	private Order buildOrder(String locale, int turnoverId) {
		Order model = new Order();
		model.setLocale(locale);
		Turnover turnover = new Turnover();
		turnover.setId(turnoverId);
		model.setTurnover(turnover);
		return model;
	}

	@RequestMapping(value = "printOrders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	boolean printOrders(@PathVariable String locale, @PathVariable int turnoverId) {
		Order model = buildOrder(locale, turnoverId);
		return customerManager.printOrders(model);
	}

	@RequestMapping(value = "checkout/{turnoverId}", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody
	boolean checkout(@PathVariable int turnoverId) {
		customerManager.checkout(turnoverId);
		return true;
	}

	@RequestMapping(value = "changeTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	boolean changeTable(@RequestBody Turnover turnover) {
		customerManager.changeTable(turnover);
		return true;
	}
}
