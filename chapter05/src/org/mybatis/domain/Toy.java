package org.mybatis.domain;

import java.io.Serializable;

public class Toy implements Serializable {
	private int toyNo;
	private String toyName;
	private int toyPrice;
	private int shopNo;

	public Toy() {
	}

	public Toy(int toyNo, String toyName, int toyPrice, int shopNo) {
		this.toyNo = toyNo;
		this.toyName = toyName;
		this.toyPrice = toyPrice;
		this.shopNo = shopNo;
	}

	public int getToyNo() {
		return toyNo;
	}

	public void setToyNo(int toyNo) {
		this.toyNo = toyNo;
	}

	public String getToyName() {
		return toyName;
	}

	public void setToyName(String toyName) {
		this.toyName = toyName;
	}

	public int getToyPrice() {
		return toyPrice;
	}

	public void setToyPrice(int toyPrice) {
		this.toyPrice = toyPrice;
	}

	public int getShopNo() {
		return shopNo;
	}

	public void setShopNo(int shopNo) {
		this.shopNo = shopNo;
	}
}