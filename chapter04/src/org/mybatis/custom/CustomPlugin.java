package org.mybatis.custom;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

@Intercepts({
    @Signature(
        type = StatementHandler.class, 
        method = "query", 
        args = { Statement.class, ResultHandler.class }
    )
})
public class CustomPlugin implements Interceptor {
    private static final Log log = LogFactory.getLog(CustomPlugin.class);

    private static final String REX = "[?]";
    private Properties properties;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler =
            (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();

        // 호출된 매핑 구문 반환
        String bindingSql = boundSql.getSql();

        // 호출된 매핑 구문에 전달된 파라미터 객체 반환
        Object parameterObject =
            statementHandler.getParameterHandler().getParameterObject();

        // 파라미터 객체의 타입이 문자나 문자열인 경우
        if (parameterObject instanceof String 
                || parameterObject instanceof Character) {
            bindingSql = bindingSql.replaceFirst(
                REX, "'" + String.valueOf(parameterObject) + "'");
        // 파라미터 객체의 타입이 숫자인 경우
        } else if (parameterObject instanceof Integer 
                || parameterObject instanceof Long
                || parameterObject instanceof Float 
                || parameterObject instanceof Double) {
            bindingSql = bindingSql.replaceFirst(
                REX, String.valueOf(parameterObject));
        // 파라미터 객체의 타입이 Map 타입인 경우
        } else if (parameterObject instanceof Map) {
            List<ParameterMapping> parameterMappinges = 
                boundSql.getParameterMappings();
            for (ParameterMapping parameterMapping : parameterMappinges) {
                String parameterKey = parameterMapping.getProperty();
                Object parameerValue = 
                    ((Map) parameterObject).get(parameterKey);

                boolean isNumber = 
                    Pattern.matches("[0-9]+", String.valueOf(parameerValue));
                if (isNumber) {
                    bindingSql = bindingSql.replaceFirst(
                        REX, String.valueOf(parameerValue));
                } else {
                    bindingSql = bindingSql.replaceFirst(
                        REX, "'" + String.valueOf(parameterObject) + "'");
                }
            }
        } else {
            List<ParameterMapping> parameterMappinges = 
                boundSql.getParameterMappings();
            Class<? extends Object> parameterHandlerClass =
                parameterObject.getClass();
            for (ParameterMapping parameterMapping : parameterMappinges) {
                String parameterKey = parameterMapping.getProperty();
                Field field = 
                    parameterHandlerClass.getDeclaredField(parameterKey);
                field.setAccessible(true);

                Class<?> javaType = parameterMapping.getJavaType();
                if (String.class == javaType) {
                    bindingSql = bindingSql.replaceFirst(
                        REX, "'" + field.get(parameterObject) + "'");
                } else {
                    bindingSql = bindingSql.replaceFirst(
                        REX, field.get(parameterObject).toString());
                }
            }
        }

        log.debug(bindingSql);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
