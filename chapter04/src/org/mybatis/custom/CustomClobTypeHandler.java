package org.mybatis.custom;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

@MappedTypes(value = String.class)
@MappedJdbcTypes(value = { JdbcType.CLOB })
public class CustomClobTypeHandler extends BaseTypeHandler<String> {
	@Override
	public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType)
			throws SQLException {
		StringReader reader = new StringReader(parameter);
		preparedStatement.setCharacterStream(i, reader, parameter.length());
	}

	@Override
	public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
		String value = "";
		Clob clob = resultSet.getClob(columnName);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1, size);
		}
		return value;
	}

	@Override
	public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
		String value = "";
		Clob clob = resultSet.getClob(columnIndex);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1, size);
		}
		return value;
	}

	@Override
	public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
		String value = "";
		Clob clob = callableStatement.getClob(columnIndex);
		if (clob != null) {
			int size = (int) clob.length();
			value = clob.getSubString(1, size);
		}
		return value;
	}
}
