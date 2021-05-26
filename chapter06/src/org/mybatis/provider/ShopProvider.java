package org.mybatis.provider;

import java.util.Map;

public class ShopProvider {
	public String select(Map<String, Object> parameters) {
        StringBuffer query = new StringBuffer("")
            .append("SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS ")
            .append("FROM SHOP ");

        Object shopNo = parameters.get("shopNo");
        if (shopNo != null && !"".equals(shopNo)) {
            query.append("WHERE SHOP_NO = #{shopNo}");
        }

        return query.toString();
	}

	public String insert(Map<String, Object> parameters) {
	    StringBuffer query = new StringBuffer("")
			.append("INSERT INTO SHOP (SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS) ")
			.append("VALUES (#{shopNo}, #{shopName}, #{shopLocation}, #{shopStatus})");

		return query.toString();
	}

	public String update(Map<String, Object> parameters) {
		StringBuffer query = new StringBuffer("")
			.append("UPDATE SHOP ")
			.append("SET SHOP_STATUS = #{shopStatus} ")
			.append("WHERE SHOP_NO = #{shopNo}");

		return query.toString();
	}

	public String delete(Map<String, Object> parameters) {
		StringBuffer query = new StringBuffer("")
			.append("DELETE FROM SHOP ")
			.append("WHERE SHOP_NO = #{shopNo}");

		return query.toString();
	}
}
