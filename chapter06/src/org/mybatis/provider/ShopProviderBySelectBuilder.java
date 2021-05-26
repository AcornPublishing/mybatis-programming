package org.mybatis.provider;

import static org.apache.ibatis.jdbc.SelectBuilder.*;
import java.util.Map;

public class ShopProviderBySelectBuilder {
    /* 조회 쿼리문 생성 및 반환 */
    public String select(Map<String, Object> parameters) {
        BEGIN();

        SELECT("SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS");
        FROM("SHOP");
        WHERE("SHOP_NO = #{shopNo}");

        return SQL();
    }
}
