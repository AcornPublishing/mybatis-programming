package org.mybatis.domain;

import java.io.Serializable;

public class Shop implements Serializable {
	private int shopNo;
	private String shopName;
	private String shopLocation;
	private String shopStatus;

	/* 기본 생성자 */
	public Shop() {
	}

	/* 생성자 */
	public Shop(int shopNo, String shopName, String shopLocation, String shopStatus) {
		this.shopNo = shopNo;
		this.shopName = shopName;
		this.shopLocation = shopLocation;
		this.shopStatus = shopStatus;
	}

	/* 셋터 메소드 */
	public void setShopNo(int shopNo) {
		this.shopNo = shopNo;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShopLocation(String shopLocation) {
		this.shopLocation = shopLocation;
	}

	public void setShopStatus(String shopStatus) {
		this.shopStatus = shopStatus;
	}

	/* 겟터 메소드 */
	public int getShopNo() {
		return shopNo;
	}

	public String getShopName() {
		return shopName;
	}

	public String getShopLocation() {
		return shopLocation;
	}

	public String getShopStatus() {
		return shopStatus;
	}
}
