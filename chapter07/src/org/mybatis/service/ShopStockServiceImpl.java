package org.mybatis.service;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.persistence.ShopStockDao;
import org.mybatis.persistence.ShopStockDaoImpl;

public class ShopStockServiceImpl implements ShopStockService {
	/* 데이터 접근 객체 */
	ShopStockDao shopStockDao = new ShopStockDaoImpl();

	/* 가게 장난감 재고량 수정 */
	public void edit(int shopNo, int amountOfStorage) {
		// 가게 장난감 재고량 조회
		int totalStock = shopStockDao.select(shopNo);

		// 가게 장난감 재고량에 입고량을 반영
		totalStock = totalStock + amountOfStorage;

		// 파라미터 객체 생성 및 등록
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("shopNo", 1);
		parameters.put("totalStock", totalStock);

		// 가게 장난감 재고량 수정
		shopStockDao.update(parameters);
	}
}