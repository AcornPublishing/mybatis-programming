package org.mybatis.persistence;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

public interface ShopMapper {
	/* 정적 매핑 구문 */
    @Select( value = { 
        "SELECT SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS ",
	    "FROM SHOP ", 
	    "WHERE SHOP_NO = #{shopNo}" })
	/* 동적 매핑 구문 */
	/* @SelectProvider(type = ShopProvider.class, method = "select") */
	@Results( value = { 
		@Result(column = "SHOP_NO", property = "shopNo", id = true),
		@Result(column = "SHOP_NAME", property = "shopName"),
		@Result(column = "SHOP_LOCATION", property = "shopLocation"),
		@Result(column = "SHOP_STATUS", property = "shopStatus")
	})
	public Map<String, Object> select(Map<String, Object> parameters);

	@SelectKey(statement = "SELECT SEQ_SHOP_NO.NEXTVAL FROM DUAL", keyColumn = "SHOP_NO", keyProperty = "shopNo", resultType = int.class, before = true)
	/* 정적 매핑 구문 */
	@Insert( value = { 
		"INSERT INTO SHOP ",
	    "(SHOP_NO, SHOP_NAME, SHOP_LOCATION, SHOP_STATUS) ", 
	    "VALUES ",
	    "(#{shopNo}, #{shopName}, #{shopLocation}, #{shopStatus})"
	})
    /* 동적 매핑 구문 */
	/* @InsertProvider(type = ShopProvider.class, method = "insert") */
	public void insert(Map<String, Object> parameters);

	/* 정적 매핑 구문 */
	@Update( value = { 
        "UPDATE SHOP ",
        "SET SHOP_STATUS = #{shopStatus} ",
        "WHERE SHOP_NO = #{shopNo}" 
    })
	/* 동적 매핑 구문 */
	/* @UpdateProvider(type = ShopProvider.class, method = "update" ) */
	public void update(Map<String, Object> parameters);

	/* 정적 매핑 구문 */
	@Delete( value = { 
		"DELETE FROM SHOP ", 
		"WHERE SHOP_NO = #{shopNo}"
	})
	/* 동적 매핑 구문 */
	/* @DeleteProvider(type = ShopProvider.class, method = "delete") */
	public void delete(Map<String, Object> parameters);
}
