package org.mybatis.service;

import java.util.List;
import javax.annotation.Resource;
import org.mybatis.domain.Shop;
import org.mybatis.persistence.ShopDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl implements ShopService {
	@Resource
	private ShopDao shopDao;

	/* 가게 목록 조회 */
	@Override
	public List<Shop> find(Shop shop) {
		// 데이터 접근 객체 호출
		return this.shopDao.list(shop);
	}

	/* 가게 목록 조회 */
	@Override
	@Transactional
	public void add(Shop shop) {
		// 데이터 접근 객체 호출
		this.shopDao.insert(shop);
	}

	/* 가게 조회 */
	@Override
	public Shop view(String shopNo) {
		// 데이터 접근 객체 호출
		return this.shopDao.select(shopNo);
	}

	/* 가게 수정 */
	@Override
	@Transactional
	public void edit(Shop shop) {
		// 데이터 접근 객체 호출
		this.shopDao.update(shop);
	}

	/* 가게 삭제 */
	@Override
	@Transactional
	public void remove(String shopNo) {
		// 데이터 접근 객체 호출
		this.shopDao.delete(shopNo);
	}
}
