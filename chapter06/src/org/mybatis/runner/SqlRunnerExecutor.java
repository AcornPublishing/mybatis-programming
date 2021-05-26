package org.mybatis.runner;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.jdbc.SqlRunner;

public class SqlRunnerExecutor {
	private static String driver = "oracle.jdbc.driver.OracleDriver";
	private static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String username = "mybatis";
	private static String password = "mybatis$";

	private static DataSource dataSource;

	static {
		try {
			dataSource = new UnpooledDataSource(driver, url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		SqlRunner sqlRunner = null;

		try {
			sqlRunner = new SqlRunner(dataSource.getConnection());
			Map<String, Object> result = sqlRunner.selectOne(
					"SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS " + "FROM SHOP WHERE SHOP_NO = ?", 1);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlRunner.closeConnection();
		}
	}
}
