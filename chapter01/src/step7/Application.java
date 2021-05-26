package step7;

import java.util.Map;

import org.mybatis.domain.Shop;

public class Application extends SqlMapper {
	public Shop view(Map<String, Object> parameters) throws Exception {
		return selectOne("select", parameters, "org.mybatis.domain.Shop");
	}
}
