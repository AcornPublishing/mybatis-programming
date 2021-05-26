package org.mybatis.persistence;

import java.util.Map;

public interface _ShopMapper {
	public Map<String, Object> select(Map<String, Object> map);
	public void insert(Map<String, Object> map);
	public void update(Map<String, Object> map);
	public void delete(Map<String, Object> map);
}
