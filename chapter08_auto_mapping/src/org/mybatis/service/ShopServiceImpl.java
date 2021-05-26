package org.mybatis.service;

import java.util.List;
import javax.annotation.Resource;
import org.mybatis.domain.Shop;
import org.mybatis.persistence.ShopMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopServiceImpl implements ShopService {
	@Resource
	private ShopMapper shopMapper;

	/* 가게 목록 조회 */
	@Override
	public List<Shop> find(Shop shop) {
		// 매퍼 객체 호출
		return this.shopMapper.list(shop);
	}

	/* 가게 등록 */
	@Override
	@Transactional
	public void add(Shop shop) {
		// 매퍼 객체 호출
		this.shopMapper.insert(shop);
	}

	/* 가게 조회 */
	@Override
	public Shop view(String shopNo) {
		// 매퍼 객체 호출
		return this.shopMapper.select(shopNo);
	}

	/* 가게 수정 */
	@Override
	@Transactional
	public void edit(Shop shop) {
		// 매퍼 객체 호출
		this.shopMapper.update(shop);
	}

	/* 가게 삭제 */
	@Override
	@Transactional
	public void remove(String shopNo) {
		// 매퍼 객체 호출
		this.shopMapper.delete(shopNo);
	}
}
