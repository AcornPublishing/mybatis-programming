package org.mybatis.provider;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class ShopProviderBySql {
	public static void main(String[] args) {
		ShopProviderBySql shopProviderBySql = new ShopProviderBySql();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shopNo", 1);
		
		String result = shopProviderBySql.select(
				parameters
				);
		
		System.out.println(result);
	}

	public String select(Map<String, Object> parameters) {
		return new SQL() {
			{
				SELECT("SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS");
				FROM("SHOP");
				WHERE("SHOP_NO = #{shopNo}");
			}
		}.toString();
	}

	public String insert(Map<String, Object> parameters) {
		return new SQL() {
			{
				INSERT_INTO("SHOP");
				VALUES("SHOP_NO", "#{shopNo}");
				VALUES("SHOP_NAME", "#{shopName}");
				VALUES("SHOP_LOCATION", "#{shopLocation}");
				VALUES("SHOP_STATUS", "#{shopStatus}");
			}
		}.toString();
	}

	public String update(Map<String, Object> parameters) {
		return new SQL() {
			{
				UPDATE("SHOP");
				SET("SHOP_STATUS = #{shopStatus}");
				WHERE("SHOP_NO = #{shopNo}");
			}
		}.toString();
	}

	public String delete(Map<String, Object> parameters) {
		return new SQL() {
			{
				DELETE_FROM("SHOP");
				WHERE("SHOP_NO = #{shopNo}");
			}
		}.toString();
	}
}
