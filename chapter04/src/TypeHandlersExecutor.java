import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.custom.CustomClobTypeHandler;

public class TypeHandlersExecutor {
	private static final Log log = LogFactory.getLog(TypeHandlersExecutor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로 (타입 핸들러 실습)
			String resource = "resources/mybatis/typehandlers/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			Configuration configuration = sqlSessionFactory.getConfiguration();

			TypeHandlerRegistry typeHandlerRegistry = 
					configuration.getTypeHandlerRegistry();

			TypeHandler<?> typeHandler = 
					typeHandlerRegistry.getMappingTypeHandler(
							CustomClobTypeHandler.class);

			log.debug(String.valueOf(typeHandler.getClass()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
