package com.sjb.wuwaechorank.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sjb.wuwaechorank.dao.entity.user.UserDao;
import com.sjb.wuwaechorank.entity.User;
import com.sjb.wuwaechorank.util.DaoJDBCUtil;

@SpringBootTest
public class UserDaoTest {
    private static final String TABLE_NAME = "user";
    @Autowired
    DaoJDBCUtil daoJDBCUtil;

    @Autowired
    UserDao userDao;

    User user1;
    User user2;
    User user3;

    @BeforeEach
    void setUp(){
        daoJDBCUtil.initTables(TABLE_NAME);

        user1 = new User(1);
        user2 = new User(2);
        user3 = new User(3);
    }

    @Test
    void addAndGet(){
        this.userDao.add(user1);
        User user = this.userDao.get(1);
        assertEquals(user1.getId(), user.getId());
    }

    @Test
    void getAll(){
        this.userDao.add(user1);
        this.userDao.add(user2);
        this.userDao.add(user3);
        List<User> users = this.userDao.getAll();
        assertEquals(3, users.size());
    }

    @Test
    void deleteAndGetCount(){
        this.userDao.add(user1);
        this.userDao.delete(1);
        assertEquals(0, this.userDao.getCount());
    }
}
