package me.wbf.hospital.dao;

import me.wbf.hospital.entity.User;

/**
 * @author Baofeng.Wu
 * @since 1.0.0
 */
public interface UserDao {
    /**
     * 检索数据库中是否已经存在此用户名
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 检索数据库中是否已经存在此email
     *
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 添加管理员到数据库
     *
     * @param user
     * @return
     */
    boolean save(User user);


}
