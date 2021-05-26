package step4;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.mybatis.domain.Shop;

public class Application extends SqlMapper {
	public Shop view(List<Object> parameters) throws Exception {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shop shop = null;

		try {
			// List 타입 객체에 담긴 파라미터를 쿼리문의 매개 변수에 바인딩
			String sql = parameterBindingByList("select", parameters);

			// 쿼리문 처리 객체 생성
			preparedStatement = connect().prepareStatement(sql);

			// 쿼리문 실행 및 결과 반환
			resultSet = preparedStatement.executeQuery();

			// 결과 처리
			if (resultSet.next()) {
				shop = new Shop();

				shop.setShopNo(resultSet.getInt("SHOP_NO"));
				shop.setShopName(resultSet.getString("SHOP_NAME"));
				shop.setShopLocation(resultSet.getString("SHOP_LOCATION"));
				shop.setShopStatus(resultSet.getString("SHOP_STATUS"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// 자원 및 데이터베이스 연결 해제
			release();
		}

		return shop;
	}
}
