package step0;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.mybatis.domain.Shop;

public class Application {
	public Shop view(List<Object> parameters) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Shop shop = null;

		try {
			// JDBC 드라이버 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try {
			// 데이터베이스 연결
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "mybatis", "mybatis$");

			// 쿼리문 처리 객체 생성
			preparedStatement = connection.prepareStatement("SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS "
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
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}

		return shop;
	}
}
