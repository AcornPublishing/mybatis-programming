import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class EnvironmentsExecutor {
	private static final Log log = LogFactory.getLog(EnvironmentsExecutor.class);

	private static SqlSessionFactory defaultSqlSessionFactory;
	private static SqlSessionFactory etcSqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로 (환경 실습)
			String resource = "resources/mybatis/environments/config-mybatis.xml";

			// 기본 환경 참조
			Reader reader = Resources.getResourceAsReader(resource);
			defaultSqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

			// etc 환경 참조
			reader = Resources.getResourceAsReader(resource);
			etcSqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "etc");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Configuration defaultConfiguration = defaultSqlSessionFactory.getConfiguration();

			Environment defaultEnvironment = defaultConfiguration.getEnvironment();
			log.debug(defaultEnvironment.getId());

			Configuration etcConfiguration = etcSqlSessionFactory.getConfiguration();
			Environment etcEnvironment = etcConfiguration.getEnvironment();
			log.debug(etcEnvironment.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
