package step2;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.mybatis.domain.Shop;

public class Executor {
	private static final Logger logger = Logger.getLogger(Executor.class.getName());

	public static void main(String[] args) {
		try {
			// 파라미터 객체 생성 및 파라미터 등록 
			List<Object> parameters = new ArrayList<Object>();
			parameters.add(1);
			parameters.add("Y");

			// 조회 쿼리문 실행 및 결과 반환
			Application application = new Application();
			Shop shop = application.view(parameters);

			logger.info(shop.getShopName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
