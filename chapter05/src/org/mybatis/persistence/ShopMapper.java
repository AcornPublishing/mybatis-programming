package org.mybatis.persistence;

import java.util.List;

import org.mybatis.domain.Shop;

public interface ShopMapper {
	List<Shop> list(Object parameter);
}
