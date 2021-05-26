package org.mybatis.runner;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptRunnerExecutor {
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
		ScriptRunner scriptRunner = null;

		try {
			scriptRunner = new ScriptRunner(dataSource.getConnection());
			scriptRunner.setAutoCommit(true);
			scriptRunner.setStopOnError(true);
			scriptRunner.runScript(Resources.getResourceAsReader("resources/mybatis/sql_script.txt"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			scriptRunner.closeConnection();
		}
	}
}
