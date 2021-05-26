package org.mybatis.custom;

import java.util.Properties;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.mybatis.domain.Shop;

public class CustomObjectFactory extends DefaultObjectFactory {
	private Properties properties;

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public <T> T create(Class<T> type) {
		Object object = create(type, null, null);

		if (Shop.class == type) {
			Shop shop = (Shop) object;

			String shopStatus = shop.getShopStatus();
			if (shopStatus == null || "".equals(shopStatus)) {
				shop.setShopStatus("N");
			}
		}

		return (T) object;
	}
}
