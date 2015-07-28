package com.betalife.sushibuffet.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

public class CorsFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		System.out.println("++CorsFilter+++++============++++++++++++++");
		response.addHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE");
		response.addHeader("Access-Control-Allow-Headers",
				"X-Requested-With,Origin,Content-Type, Accept");
		response.setHeader("Access-Control-Max-Age", "3600");
		filterChain.doFilter(request, response);
	}

}