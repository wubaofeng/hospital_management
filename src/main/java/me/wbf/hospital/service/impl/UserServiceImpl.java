package me.wbf.hospital.service.impl;

import me.wbf.hospital.dao.UserDao;
import me.wbf.hospital.dao.impl.UserDaoImpl;
import me.wbf.hospital.entity.User;
import me.wbf.hospital.service.UserService;

/**
 * UserService的实现类
 *
 * @author Baofeng.Wu
 * @since 1.0.0
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean existsByUsername(String username) {
        return userDao.findByUsername(username) == null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.findByEmail(email)  == null;
    }

    @Override
    public boolean save(User user) {
        return userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
