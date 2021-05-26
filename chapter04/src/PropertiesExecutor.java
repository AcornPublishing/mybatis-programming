import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class PropertiesExecutor {
	private static final Log log = LogFactory.getLog(PropertiesExecutor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로 (프로퍼티 실습)
			String resource = "resources/mybatis/properties/config-mybatis.xml";

			// 프로퍼티 객체 생성 및 프로퍼티 등록
			Properties properties = new Properties();
			properties.put("username", "ibatis");

			Reader reader = Resources.getResourceAsReader(resource);
//			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Configuration configuration = sqlSessionFactory.getConfiguration();

			Properties properties = configuration.getVariables();

			log.debug(properties.getProperty("username"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
