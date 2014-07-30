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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.betalife.sushibuffet.manager.CustomerManager;
import com.betalife.sushibuffet.model.Categories;
import com.betalife.sushibuffet.model.Diningtable;
import com.betalife.sushibuffet.model.Turnovers;

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
	List<Diningtable> fetchAllTables() {
		System.out.println("fetchAllTables");
		List<Diningtable> allTables = customerManager.getAvailableTables();
		return allTables;
	}

	@RequestMapping(value = "openTable", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public @ResponseBody
	List<Categories> openTable(@RequestBody Turnovers turnover) {
		customerManager.openTable(turnover.getTableId(), turnover.getCustomerCount());
		return customerManager.getCategoriesByParentId(0);
	}

}
