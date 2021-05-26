package step2;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mybatis.domain.Shop;

public class Application extends SqlMapper {
	public Shop view(List<Object> parameters) throws SQLException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shop shop = null;

		try {
			// 쿼리문 처리 객체 생성
			preparedStatement = connect().prepareStatement("SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS "
					+ "FROM SHOP " + "WHERE SHOP_NO = ? AND SHOP_STATUS = ?");

			// 파라미터 바인딩
			preparedStatement.setInt(1, (Integer) parameters.get(0));
			preparedStatement.setString(2, String.valueOf(parameters.get(1)));

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
		} catch (SQLException e) {
			throw e;
		} finally {
			// 자원 및 데이터베이스 연결 해제
			release();
		}

		return shop;
	}
}
