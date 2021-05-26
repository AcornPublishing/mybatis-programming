import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.domain.Shop;

public class Executor {
	private static final Log log = LogFactory.getLog(Executor.class);

	private static SqlSessionFactory sqlSessionFactory;

	static {
		try {
			// 마이바티스 환경 설정 XML 파일 경로
			String resource = "resources/mybatis/config-mybatis.xml";
			Reader reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// 세션 및 트랜잭션 시작
		SqlSession sqlSession = sqlSessionFactory.openSession();

		try {
			// 파라미터 객체 생성 및 파라미터 등록
            Shop shop = new Shop();
            shop.setShopNo(4);

            sqlSession.delete("org.mybatis.persistence.ShopMapper.delete", shop);


/*          // 파라미터 객체 생성 및 파라미터 등록
            Shop shop = new Shop();
            shop.setShopNo(4);
            shop.setShopStatus("N");

            sqlSession.update("org.mybatis.persistence.ShopMapper.update", shop);
*/

/*          // 파라미터 객체 생성 및 파라미터 등록
            Shop shop = new Shop();
            shop.setShopNo(4);

			// 조회 매핑 구문 실행
            shop = sqlSession.selectOne("org.mybatis.persistence.ShopMapper.select", shop);
            log.debug(shop.getShopName());
*/

/*			// 파라미터 객체 생성 및 파라미터 등록
            Shop shop = new Shop();
			shop.setShopNo(4);
			shop.setShopName("Dad Store");
			shop.setShopLocation("D Tower Secho dong");
			shop.setShopStatus("Y");

            // 등록 매핑 구문 실행
			sqlSession.insert("org.mybatis.persistence.ShopMapper.insert", shop);
*/

            // 트랜잭션 커밋
            sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			// 트랜잭션 커밋
			sqlSession.rollback();
		} finally {
			// 세션 및 트랜잭션 종료
			sqlSession.close();
		}
	}
}
