package com.sjb.wuwaechorank.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.sjb.wuwaechorank.customannotation.PrimaryKey;



/**
 * 테스트에서 공통되는 JDBCtemplate의 기능을 따로 모아놓은 유틸
 * 클래스입니다.
*/
@Component
public class DaoJDBCUtil {
    /**
     * 유틸에서 DB와 연결하고 제어하기위한 JdbcTemplate
     */
    private final JdbcTemplate jdbcTemplate;
    /**
     * 외래키가 있는 엔티티의 테스트를 할 경우 필요한 테스트픽스쳐
     */
    private TestFixture testFixture;

    public DaoJDBCUtil(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 클래스에서 사용할 {@code TestFixture}를 할당합니다.
     * {@code TestFixture}가 있어야 전체 기능을 사용할 수 있습니다.
     * @param testFixture 참조 엔티티와 DAO를 담고있는 객체
     */
    public void setTestFixture(TestFixture testFixture){
        this.testFixture = testFixture;
    }
    
    /**
     * DB 테이블들을 완전히 초기화합니다.
     * @param tableNames 초기화할 테이블 목록
     */
    public void initTables(String... tableNames){
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (String tableName : tableNames) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ tableName);
        }
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }
    
    /**
     * 테스트하려는 엔티티가 외래키로 참조하고 있는 테이블들을 초기화합니다.
     * <p><b>TestFixtrue가 반드시 할당되어 있어야합니다.
     */
    public void initReferenceTables(){
        Map<Object, Object> refEntityAndDao = testFixture.getReferenceEntityAndDaoMap();
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0");
        for (Object entity : refEntityAndDao.keySet()) {
            this.jdbcTemplate.execute("TRUNCATE TABLE "+ entity.getClass().getSimpleName());
            this.daoMethodInvoke(entity, "add" , entity);
        } 
        this.jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    /**
     * 테스트 대상 엔티티가 참조하는 엔티티를 지우는 함수입니다.<br>
     * 파라미터로 받은 클래스의 엔티티를 찾아 제거합니다.
     * <p><b>TestFixtrue가 반드시 할당되어 있어야합니다.</b>
     * @param entityClass 제거할 참조 엔티티 클래스
     */
    public void deleteRefEntity(Class<?> entityClass){
        Object key = this.testFixture.refEntityAndDao.keySet().stream()
                    .filter(refentity->refentity.getClass().getSimpleName().equals(entityClass.getSimpleName()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("deleteEntity()의 매개변수로 잘못된 클래스를 전달받았습니다."));
        
        Object primaryKey = Arrays.stream(key.getClass().getDeclaredFields())
                            .filter(field->field.isAnnotationPresent(PrimaryKey.class))
                            .map(field->this.fieldGet(field, key))
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException("PrimaryKey 어노테이션이 존재하지 않습니다. " + key.getClass().getSimpleName() + " 엔티티를 확인해 주세요."));
        this.daoMethodInvoke(key, "delete", primaryKey);
    }
    
    /**
     * PrimaryKey의 값을 가져오기 위한 함수로 stream에서는 예외처리가 안되어서 따로 빼낸 함수.<br>
     * 동작은 Field.get()과 동일하다.
     * 
     * @param field 값을 가져올 필드로 {@Code @PrimaryKey} 어노테이션이 있는 필드가 들어온다.
     * @param key 값을 가져올 엔티티이다.
     * @return {@Code @PrimaryKey}가 붙은 필드의 값을 가져온다.
     */
    private Object fieldGet(Field field, Object key){
        try {
            field.setAccessible(true);
            Object primaryKey =  field.get(key);
            return primaryKey;
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("필드에 접근할 수 없습니다.", e);
        }
    }

    /**
     * DAO의 add() 등의 DB 제어 함수를 동작시키기 위한 함수.
     * <p>반복적으로 사용되고 처리할 예외가 많아 따로 빼놓았다.
     * @param key key와 관련있는 DAO를 가져온다.
     * @param methodName DAO로 실행할 메서드 이름
     * @param param 메서드 실행에 필요한 파라미터
     */
    public void daoMethodInvoke(Object key, String methodName, Object... param){
        Object dao = this.testFixture.refEntityAndDao.get(key);
        try{
            Method daoMethod = dao.getClass().getMethod(methodName,Object.class);
            daoMethod.invoke(dao, param);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException(dao.getClass().getSimpleName() + "는 "+ methodName + "()가 존재하지 않습니다.", e);
        } catch (IllegalAccessException e){
            throw new IllegalStateException(dao.getClass().getSimpleName() + "의 "+ methodName + "()에 대한 접근이 잘 못되었습니다.", e);
        } catch (InvocationTargetException e){
            throw new IllegalCallerException(dao.getClass().getSimpleName() + "의 "+ methodName + "()를 실행 중 내부에서 예외가 발생했습니다.", e);
        }
    }
}