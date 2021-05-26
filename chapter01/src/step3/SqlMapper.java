package step3;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class SqlMapper {
	/* 데이터베이스 설정 프로퍼티 파일 경로 */
	private String configurationResource = "resources/jdbc/config-jdbc.properties";
	private Properties configuration = new Properties();

	/* 쿼리문 프로퍼티 파일 경로 */
	private String sqlResource = "resources/jdbc/sql.properties";
	private Properties sql = new Properties();

	private Connection connection;
	private PreparedStatement preparedStatement;
	private ResultSet resultSet;

	/* 기본 생성자 */
	public SqlMapper() {
		try {
			// 데이터베이스 설정 프로퍼티 파일 로딩 메소드 호출
			configurationAsProperties();

			// 쿼리문 프로퍼티 파일 로딩 메소드 호출
			sqlAsProperties();

			// JDBC 드라이버 로딩
			Class.forName(configuration.getProperty("driver"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* 데이터베이스 연결 */
	protected Connection connect() throws SQLException {
		try {
			if (connection == null) {
				connection = DriverManager.getConnection(configuration.getProperty("url"),
						configuration.getProperty("username"), configuration.getProperty("password"));
			}

			return connection;
		} catch (SQLException e) {
			throw e;
		}
	}

	/* 자원 및 데이터베이스 연결 해제 */
	protected void release() {
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

	/* 데이터베이스 설정 프로퍼티 파일 로딩 */
	private void configurationAsProperties() throws IOException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream(configurationResource);

		try {
			configuration.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			inputStream.close();
		}
	}

	/* 쿼리문 프로퍼티 파일 로딩 */
	private void sqlAsProperties() throws IOException {
		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

		InputStream inputStream = contextClassLoader.getResourceAsStream(sqlResource);

		try {
			sql.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			inputStream.close();
		}
	}

	/* 쿼리문 반환 */
	protected String sqlById(String sqlId) {
		return sql.getProperty(sqlId);
	}
}
