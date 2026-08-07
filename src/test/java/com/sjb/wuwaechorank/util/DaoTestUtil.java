package com.sjb.wuwaechorank.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLIntegrityConstraintViolationException;

import org.junit.jupiter.api.function.Executable;
import org.springframework.dao.DataIntegrityViolationException;

public class DaoTestUtil {
    /** 
     * @param executable
     */
    public static void foreignKeyConstraintViolationTest(Executable executable){
        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, executable);
        
        Throwable cause = exception.getMostSpecificCause();
        assertInstanceOf(SQLIntegrityConstraintViolationException.class, cause);

        SQLIntegrityConstraintViolationException sqlException = (SQLIntegrityConstraintViolationException) cause;
        assertEquals(DaoSqlErrorCode.FOREIGN_KEY_CONSTRAINT_FAIL, sqlException.getErrorCode());
    }
}
