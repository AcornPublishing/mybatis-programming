import java.io.Reader;
import java.util.Calendar;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class RunEhcache {
	private static SqlSessionFactory sqlSessionFactory;

	/**
	 * 지정된 경로에서 마이바티스 환경 설정 파일 정보를 읽어, SqlSessionFactory 인터페이스를 구현한
	 * DefaultSqlSessionFactory 객체를 생성한다.
	 */
	static {
		try {
			// 마이바티스 환경 설정 파일 경로를 지정한다.
			String resource = "chapter5/resources/mybatis/config-mybatis.xml";
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

		long intervalTime = 0;
		for (int count = 0; count < 10000; count++) {
			SqlSession sqlSession = sqlSessionFactory.openSession();
			long startTime = Calendar.getInstance().getTimeInMillis();

			try {
				sqlSession.selectList("chapter5.org.mybatis.persistence.ShopMapper.selectListShop");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sqlSession.close();
			}

			long endTime = Calendar.getInstance().getTimeInMillis();
			intervalTime = intervalTime + (endTime - startTime);
		}

		System.out.println("» wait time is : " + (intervalTime));
	}
}