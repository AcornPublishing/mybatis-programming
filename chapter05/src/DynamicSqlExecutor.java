import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DynamicSqlExecutor {
	private static final Log log = LogFactory.getLog(DynamicSqlExecutor.class);

	private static SqlSessionFactory sqlSessionFactory;

	/**
	 * 지정된 경로에서 마이바티스 환경 설정 파일 정보를 읽어, SqlSessionFactory 인터페이스를 구현한
	 * DefaultSqlSessionFactory 객체를 생성한다.
	 */
	static {
		try {
			String resource = "resources/mybatis/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);

			Properties properties = new Properties();
			properties.put("username", "mybatis");

			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String CR = "\r\n";

		// 'WHERE' 파라미터 생성
		Map parameters = new HashMap();
		parameters.put("shop_no", "1");
		parameters.put("shop_status", "Y");

		// 동적 쿼리 구문 생성
		StringBuilder dynamicSql = new StringBuilder();
		dynamicSql.append("SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS").append(CR);
		dynamicSql.append("FROM   SHOP").append(CR);

		Set<String> keySet = parameters.keySet();
		Iterator keys = keySet.iterator();

		// 'WHERE' 조건 구문 처리를 위해 선언
		boolean isFirst = true;
		while (keys.hasNext()) {
			if (isFirst) {
				dynamicSql.append("WHERE ");
				isFirst = false;
			} else {
				dynamicSql.append("  AND ");
			}

			// 조건 컬럼명과 파라미터 값
			Object column = (Object) keys.next();
			String parameter = (String) parameters.get(column);
			dynamicSql.append(" " + column.toString().toUpperCase());
			dynamicSql.append(" = '");
			dynamicSql.append(parameter + "'").append(CR);
		}

		log.debug(dynamicSql.toString());
	}
}
