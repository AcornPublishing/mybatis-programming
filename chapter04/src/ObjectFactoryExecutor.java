import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.domain.Shop;

public class ObjectFactoryExecutor {
	private static final Log log = LogFactory.getLog(ObjectFactoryExecutor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로 (오브젝트 팩토리 실습)
			String resource = "resources/mybatis/objectfactory/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			ObjectFactory objectFactory = sqlSessionFactory.getConfiguration().getObjectFactory();
			Shop shop = objectFactory.create(Shop.class);

			log.debug(shop.getShopStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
