import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.mybatis.domain.Shop;

public class JdbcExecutor {
	private static final Log log = LogFactory.getLog(JdbcExecutor.class);

	public static void main(String[] args) {
		int shopNo = 1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "mybatis", "mybatis$");

			preparedStatement = connection.prepareStatement("SELECT * FROM SHOP WHERE SHOP_NO = ?");

			preparedStatement.setInt(1, shopNo);

			resultSet = preparedStatement.executeQuery();

			Shop shop = null;
			while (resultSet.next()) {
				shop = new Shop();
				shop.setShopNo(resultSet.getInt("SHOP_NO"));
				shop.setShopName(resultSet.getString("SHOP_NAME"));
				shop.setShopLocation(resultSet.getString("SHOP_LOCATION"));
				shop.setShopStatus(resultSet.getString("SHOP_STATUS"));
				log.debug(shop.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
