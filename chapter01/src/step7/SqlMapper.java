package step7;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

public class SqlMapper {
	/* 데이터베이스 설정 프로퍼티 파일 경로 */
	private String configurationResource = "resources/jdbc/config-jdbc.properties";
	private Properties configuration = new Properties();

	/* 쿼리문 프로퍼티 파일 경로 */
	private String sqlResource = "resources/jdbc/sql-inline.properties";
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

	/* List 타입 객체에 담긴 파라미터를 쿼리문의 매개 변수에 바인딩 */
	protected String parameterBindingByList(String sqlId, List<Object> parameters) throws Exception {
		try {
			// 쿼리문 반환
			String query = sqlById(sqlId);

			String[] querySplit = query.split("[?]");

			String value = "";
			StringBuilder replaceSql = new StringBuilder("");

			// 쿼리문에 지정한 매개 변수가 여러 개인 경우
			if (querySplit.length > 1) {
				for (int i = 0; i < parameters.size(); i++) {
					replaceSql.append(querySplit[i]);

					value = (parameters.get(i) == null) ? "" : parameters.get(i).toString();

					// 파라미터 값이 숫자 타입 경우 true를 반환하고, 문자 타입 경우 false를 반환한다.
					boolean isNumber = Pattern.matches("[0-9]+", value);
					if (isNumber) {
						replaceSql.append(value);
					} else {
						replaceSql.append("'").append(value).append("'");
					}

					if (!querySplit[0].contains("SELECT") && i == (parameters.size() - 1)) {
						replaceSql.append(")");
					}
				}
				// 쿼리문에 지정한 매개 변수가 하나인 경우
			} else if (querySplit.length == 1) {
				replaceSql.append(querySplit[0]);

				value = (parameters.get(0) == null) ? "" : parameters.get(0).toString();

				// 파라미터 값이 숫자 타입 경우 true를 반환하고, 문자 타입 경우 false를 반환한다.
				boolean isNumber = Pattern.matches("[0-9]+", value);
				if (querySplit[0].contains("WHERE")) {
					if (isNumber) {
						replaceSql.append(value);
					} else {
						replaceSql.append("'").append(value).append("'");
					}
				}
			}

			return replaceSql.toString();
		} catch (Exception e) {
			throw e;
		}
	}

	/* Map 타입 객체에 담긴 파라미터를 쿼리문의 매개 변수에 바인딩 */
	protected String parameterBindingByMap(String sqlId, Map<String, Object> parameters) throws Exception {
		try {
			// 쿼리문 반환
			String query = sqlById(sqlId);

			// 파라미터가 존재하는 경우
			if (parameters.size() > 0) {
				String key = "";
				String value = "";

				Iterator<String> iteratorKey = parameters.keySet().iterator();
				while (iteratorKey.hasNext()) {
					key = iteratorKey.next();
					value = (parameters.get(key) == null) ? "" : parameters.get(key).toString();

					// 파라미터 값이 숫자 타입 경우 true를 반환하고, 문자 타입 경우 false를 반환한다.
					boolean isNumber = Pattern.matches("[0-9]+", value);
					if (isNumber) {
						query = query.replaceAll("#\\{" + key + "\\}", value);
					} else {
						query = query.replaceAll("#\\{" + key + "\\}", "'" + value + "'");
					}
				}
			}

			return query;
		} catch (Exception e) {
			throw e;
		}
	}

	/* 쿼리문 실행 결과를 리절트 타입의 객체에 바인딩 */
	protected static <T> T resultByType(ResultSet resultSet, String type) throws Exception {
		try {
			List<Object> fieldNames = new ArrayList<Object>();
			List<Object> fieldTypes = new ArrayList<Object>();
			List<Object> filedValues = new ArrayList<Object>();

			if (resultSet.next()) {
				for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
					// 컬럼 명 추출한 다음 프로퍼티 명으로 변환
					String column = resultSet.getMetaData().getColumnName(i);

					String[] columnSplit = column.split("[_]");
					StringBuilder camelColumn = new StringBuilder();
					for (int j = 0; j < columnSplit.length; j++) {
						if (j == 0) {
							camelColumn.append(columnSplit[j].toLowerCase());
						} else {
							camelColumn.append(columnSplit[j].toUpperCase().charAt(0));
							camelColumn.append(columnSplit[j].substring(1, columnSplit[j].length()).toLowerCase());
						}
					}

					// 컬럼 값을 추출한 다음 프로퍼티 값으로 등록
					fieldNames.add(camelColumn);

					// 컬럼 타입을 추출한 다음 프로퍼터 타입으로 변환
					filedValues.add(resultSet.getString(column));

					// 컬럼 타입을 추출한 다음 프로퍼터 타입으로 변환
					switch (resultSet.getMetaData().getColumnType(i)) {
					case 2:
						fieldTypes.add(int.class);
						break;
					case 12:
						fieldTypes.add(String.class);
						break;
					default:
						fieldTypes.add(String.class);
						break;
					}
				}
			}

			Class objectType = Class.forName(type);
			Class[] filedType = new Class[fieldNames.size()];
			for (int i = 0; i < fieldNames.size(); i++) {
				filedType[i] = (Class) fieldTypes.get(i);
			}

			Constructor constructor = objectType.getConstructor(filedType);
			Object listConstructor[] = new Object[fieldNames.size()];
			for (int i = 0; i < fieldNames.size(); i++) {
				if (filedType[i] == int.class) {
					listConstructor[i] = Integer.parseInt((String) filedValues.get(i));
				} else {
					listConstructor[i] = (String) filedValues.get(i);
				}
			}

			// 리절트 객체 생성 및 반환
			return (T) constructor.newInstance(listConstructor);
		} catch (Exception e) {
			throw e;
		}
	}

	/* 데이터 한 건 조회 */
	public <T> T selectOne(String sqlId, Map<String, Object> parameters, String type) throws Exception {
		try {
			// Map 타입 객체에 담긴 파라미터를 쿼리문의 매개 변수에 바인딩
			String sql = parameterBindingByMap(sqlId, parameters);

			// 쿼리문 처리 객체 생성
			preparedStatement = connect().prepareStatement(sql);

			// 쿼리문 실행 및 결과 반환
			resultSet = preparedStatement.executeQuery();

			// 결과 처리
			return resultByType(resultSet, type);
		} catch (Exception e) {
			throw e;
		} finally {
			// 자원 및 데이터베이스 연결 해제
			release();
		}
	}
}
