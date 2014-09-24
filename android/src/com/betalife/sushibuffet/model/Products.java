package com.betalife.sushibuffet.model;

public class Products extends BaseModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int categoryId;
	private String description;
	private int active;
	private String thumb;
	private int position;
	private String productName;
	private String productNum;
	private int minNum;
	private int num;
	private int taxgroupId;
	private float productPrice;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getThumb() {
		return thumb;
	}

	public void setThumb(String thumb) {
		this.thumb = thumb;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNum() {
		return productNum;
	}

	public void setProductNum(String productNum) {
		this.productNum = productNum;
	}

	public int getMinNum() {
		return minNum;
	}

	public void setMinNum(int minNum) {
		this.minNum = minNum;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getTaxgroupId() {
		return taxgroupId;
	}

	public void setTaxgroupId(int taxgroupId) {
		this.taxgroupId = taxgroupId;
	}

	public float getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}

	@Override
	public String toString() {
		return "Products [categoryId=" + categoryId + ", description=" + description + ", active=" + active
				+ ", thumb=" + thumb + ", position=" + position + ", productName=" + productName
				+ ", productNum=" + productNum + ", minNum=" + minNum + ", num=" + num + ", taxgroupId="
				+ taxgroupId + ", productPrice=" + productPrice + ", id=" + id + "]";
	}

}
