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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betalife.sushibuffet.exchange.BaseExchange;
import com.betalife.sushibuffet.exchange.BooleanExchange;
import com.betalife.sushibuffet.exchange.CategoryListExchange;
import com.betalife.sushibuffet.exchange.ConstantExchange;
import com.betalife.sushibuffet.exchange.DiningtableListExchange;
import com.betalife.sushibuffet.exchange.DodoroException;
import com.betalife.sushibuffet.exchange.OrderListExchange;
import com.betalife.sushibuffet.exchange.ProductListExchange;
import com.betalife.sushibuffet.exchange.TurnoverExchange;
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

	@RequestMapping(value = "availableTables", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	DiningtableListExchange fetchAllTables() {
		List<Diningtable> allTables = customerManager.getTables();
		DiningtableListExchange exchange = new DiningtableListExchange();
		exchange.setList(allTables.toArray(new Diningtable[0]));
		return exchange;
	}

	@RequestMapping(value = "categories/{locale}/{parentId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	CategoryListExchange fetchRootCategories(@PathVariable String locale, @PathVariable int parentId) {
		Category model = new Category();
		model.setParentId(parentId);
		model.setLocale(locale);
		List<Category> all = customerManager.getCategoriesByParentId(model);

		CategoryListExchange exchange = new CategoryListExchange();
		exchange.setList(all.toArray(new Category[0]));
		return exchange;
	}

	@RequestMapping(value = "openTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	TurnoverExchange openTable(@RequestBody Turnover turnover) {
		customerManager.openTable(turnover);

		TurnoverExchange exchange = new TurnoverExchange();
		exchange.setModel(turnover);
		return exchange;
	}

	@RequestMapping(value = "constant", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ConstantExchange fetchConstants() {
		Constant constant = customerManager.getConstant();

		ConstantExchange exchange = new ConstantExchange();
		exchange.setModel(constant);
		return exchange;
	}

	@RequestMapping(value = "products/{locale}/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	ProductListExchange fetchProductsByCategoryId(@PathVariable String locale, @PathVariable int categoryId) {
		Product model = new Product();
		model.setCategoryId(categoryId);
		model.setLocale(locale);
		List<Product> all = customerManager.getProductsByCategoryId(model);

		ProductListExchange exchange = new ProductListExchange();
		exchange.setList(all.toArray(new Product[0]));
		return exchange;
	}

	@RequestMapping(value = "takeOrders/{locale}", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	BooleanExchange takeOrders(@PathVariable String locale, @RequestBody List<Order> orders) throws Exception {
		customerManager.takeOrders(orders);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	@RequestMapping(value = "orders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	OrderListExchange orders(@PathVariable String locale, @PathVariable int turnoverId) {
		Order model = buildOrder(locale, turnoverId);
		List<Order> all = customerManager.getOrders(model);

		OrderListExchange exchange = new OrderListExchange();
		exchange.setList(all.toArray(new Order[0]));
		return exchange;
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
	BooleanExchange printOrders(@PathVariable String locale, @PathVariable int turnoverId) throws Exception {
		return printOrders(locale, turnoverId, false);
	}

	private BooleanExchange printOrders(String locale, int turnoverId, boolean kitchen) throws Exception {
		Order model = buildOrder(locale, turnoverId);
		customerManager.printOrders(model, kitchen);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	@RequestMapping(value = "printKitchenOrders/{locale}/{turnoverId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	BooleanExchange printKitchenOrders(@PathVariable String locale, @PathVariable int turnoverId)
			throws Exception {
		return printOrders(locale, turnoverId, true);
	}

	@RequestMapping(value = "turnover", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	BooleanExchange updateTurnover(@RequestBody Turnover turnover) {
		customerManager.update(turnover);
		BooleanExchange exchange = new BooleanExchange();
		exchange.setModel(true);
		return exchange;
	}

	@RequestMapping(value = "ledger/{from}/{to}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody
	Map<String, Object> ledger(@PathVariable String from, @PathVariable String to) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = sdf.parse(from);
		Date toDate = sdf.parse(to);

		Map<String, Object> map = customerManager.getOrdersByDate(fromDate, toDate);
		return map;
	}

	@ExceptionHandler(Exception.class)
	public @ResponseBody
	BaseExchange exception(Exception e) {
		logger.error(e.getMessage(), e);
		DodoroException ex = new DodoroException(e);
		BaseExchange exchange = new BaseExchange();
		exchange.setException(ex);
		return exchange;
	}
}
