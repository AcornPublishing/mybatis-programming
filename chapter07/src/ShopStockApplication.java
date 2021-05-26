import org.mybatis.service.ShopStockService;
import org.mybatis.service.ShopStockServiceImpl;

public class ShopStockApplication {
	/* 서비스 객체 */
	private static ShopStockService shopStockService = new ShopStockServiceImpl();

	public static void main(String[] args) {
		// 가게 장난감 입고량
		int amountOfStorage = Integer.parseInt(args[0]);
		
		// 가게 장난감 재고량 수정
		if (amountOfStorage > 0) {
			shopStockService.edit(1, amountOfStorage);
		}
	}
}
