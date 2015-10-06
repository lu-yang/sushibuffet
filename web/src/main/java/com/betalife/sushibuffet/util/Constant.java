package com.betalife.sushibuffet.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constant {
	@Value("#{constants['waiter.password']}")
	private String password;

	@Value("#{constants['default.thumb']}")
	private String defaultThumb;

	@Value("#{constants['category.root.url']}")
	private String categoryRootUrl;

	@Value("#{constants['product.root.url']}")
	private String productRootUrl;

	private int rounds;

	private int roundInterval;

	public int getRoundInterval() {
		return roundInterval;
	}

	public void setRoundInterval(int roundInterval) {
		this.roundInterval = roundInterval;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	public String getDefaultThumb() {
		return defaultThumb;
	}

	public void setDefaultThumb(String defaultThumb) {
		this.defaultThumb = defaultThumb;
	}

	public String getCategoryRootUrl() {
		return categoryRootUrl;
	}

	public void setCategoryRootUrl(String categoryRootUrl) {
		this.categoryRootUrl = categoryRootUrl;
	}

	public String getProductRootUrl() {
		return productRootUrl;
	}

	public void setProductRootUrl(String productRootUrl) {
		this.productRootUrl = productRootUrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Constant [password=" + password + ", defaultThumb=" + defaultThumb + ", categoryRootUrl="
				+ categoryRootUrl + ", productRootUrl=" + productRootUrl + ", rounds=" + rounds
				+ ", roundInterval=" + roundInterval + "]";
	}

}
