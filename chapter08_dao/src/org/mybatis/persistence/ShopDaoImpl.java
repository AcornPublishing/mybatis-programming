package org.mybatis.persistence;

import java.util.List;
import javax.annotation.Resource;
import org.mybatis.domain.Shop;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShopDaoImpl implements ShopDao {
	@Resource
	private SqlSessionTemplate sqlSessionTemplate;

	/* 가게 목록 조회 */
	@Override
	public List<Shop> list(Shop shop) {
		// 마이바티스 객체 호출
		return this.sqlSessionTemplate.selectList("org.mybatis.persistence.ShopMapper.list", shop);
	}

	/* 가게 등록 */
	@Override
	public void insert(Shop shop) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.insert("org.mybatis.persistence.ShopMapper.insert", shop);
	}

	/* 가게 조회 */
	@Override
	public Shop select(String shopNo) {
		// 마이바티스 객체 호출
		return this.sqlSessionTemplate.selectOne("org.mybatis.persistence.ShopMapper.select", shopNo);
	}

	/* 가게 수정 */
	@Override
	public void update(Shop shop) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.update("org.mybatis.persistence.ShopMapper.update", shop);
	}

	/* 가게 삭제 */
	@Override
	public void delete(String shopNo) {
		// 마이바티스 객체 호출
		this.sqlSessionTemplate.delete("org.mybatis.persistence.ShopMapper.delete", shopNo);
	}
}
