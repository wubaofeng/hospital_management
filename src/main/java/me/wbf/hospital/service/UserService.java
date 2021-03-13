package me.wbf.hospital.service;

import me.wbf.hospital.entity.User;

/**
 * 管理员
 *
 * @author Baofeng.Wu
 * @since 1.0.0
 */
public interface UserService {
    /**
     * 检索数据库中是否已经存在此用户名
     *
     * @param username
     * @return
     */
    boolean existsByUsername(String username);

    /**
     * 检索数据库中是否已经存在此email
     *
     * @param email
     * @return
     */
    boolean existsByEmail(String email);

    /**
     * 添加管理员到数据库
     *
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 根据用户名检索用户
     * @param username
     * @return
     */
    User findByUsername(String username);
}
