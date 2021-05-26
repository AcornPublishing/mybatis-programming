package org.mybatis.persistence;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

public class ShopStockDaoImpl implements ShopStockDao {
	private SqlSession sqlSession;

	/* 가게 장난감 재고량 조회 */
	public int select(int shopNo) {
		return sqlSession.selectOne("org.mybatis.persistence.ShopStockMapper.select", shopNo);
	}

	/* 가게 장난감 재고량 수정 */
	public void update(Map<String, Object> parameters) {
		sqlSession.update("org.mybatis.persistence.ShopStockMapper.update", parameters);
	}
}