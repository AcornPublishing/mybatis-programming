import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.domain.Shop;

public class MyBatisSupport {
	private static SqlSessionFactory sqlSessionFactory;

	// public static Logger logger =
	// Logger.getLogger(MyBatisSupport.class.getName());
	/**
	 * 지정된 경로에서 마이바티스 환경 설정 파일 정보를 읽어, SqlSessionFactory 인터페이스를 구현한
	 * DefaultSqlSessionFactory 객체를 생성한다.
	 */
	static {
		try {
			// 마이바티스 환경 설정 파일 경로를 지정한다.
			String resource = "resources/mybatis/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);

			// 기본(디폴트) 환경 기반으로 SqlSessionFactory 인터페이스를 구현한
			// DefaultSeqlSessionFactory 객체를 생성한다.
			Properties properties = new Properties();
			properties.put("database_user", "mybatis");

			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		// 기본(디폴트) 환경 기반으로 SqlSession 인터페이스를 구현한 DefaultSqlSession 객체를 생성한다.
		SqlSession sqlSession = sqlSessionFactory.openSession();

		Shop shop = new Shop();
		shop.setShopNo(1);
		// -Djava.util.logging.config.file=./../standalone/src/logging.properties

		try {
			Map parameters = new HashMap();
			parameters.put("shopNo", 1);

			sqlSession.insert("org.mybatis.persistence.ShopMapper.insert", parameters);

			System.out.println(parameters);

			//shop = sqlSession.selectOne("org.mybatis.persistence.ShopMapper.selectShop", shop);
			// logger.setLevel(Level.INFO);
			// logger.info(shop.toString());
			// RowBounds rowBounds = new RowBounds(10, 10);
			// List list =
			// sqlSession.selectList("chapter5.org.mybatis.persistence.BoardMapper.selectListBoard",
			// new HashMap(), rowBounds);
			// System.out.println(list.size());
			// System.out.println(shop.getShopStatus());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			sqlSession.close();
		}
	}
}