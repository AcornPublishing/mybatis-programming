import java.io.Reader;
import java.util.Calendar;
import java.util.logging.Logger;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class CacheExecutor {
	private static final Logger logger = Logger.getLogger("CacheExecutor.class");

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로
			String resource = "resources/mybatis/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		SqlSession sqlSession = null;

		try {
			// 세션 및 트랜잭션 시작
			sqlSession = sqlSessionFactory.openSession();

			long startTime = Calendar.getInstance().getTimeInMillis();

			// 목록 조회 매핑 구문 100,000번 실행
			for (int count = 0; count < 100000; count++) {
				sqlSession.selectList("org.mybatis.persistence.ShopMapper.list");
			}

			long endTime = Calendar.getInstance().getTimeInMillis();

			logger.info((endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 세션 및 트랜잭션 종료
			sqlSession.close();
		}
	}
}
