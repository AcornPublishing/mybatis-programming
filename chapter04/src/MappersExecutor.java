import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MappersExecutor {
	private static final Log log = LogFactory.getLog(MappersExecutor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로 (매퍼 실습)
			String resource = "resources/mybatis/mappers/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SqlSession sqlSession = null;

		try {
			// 세션 및 트랜잭션 시작
			sqlSession = sqlSessionFactory.openSession();

			// 파라미터 객체 생성 및 파라미터 등록
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("shopNo", 1);

			// 조회 매핑 구문 실행 및 결과 반환
			Map<String, Object> shop = sqlSession.selectOne(
					"org.mybatis.persistence.ShopMapper.select", parameters);

			log.debug(String.valueOf(shop));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 세션 및 트랜잭션 종료
			sqlSession.close();
		}
	}
}
