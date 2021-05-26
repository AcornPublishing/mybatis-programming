package org.mybatis.persistence;

import java.util.Map;

public interface ShopStockDao {
	public int select(int shopNo);
	public void update(Map<String, Object> parameters);
}