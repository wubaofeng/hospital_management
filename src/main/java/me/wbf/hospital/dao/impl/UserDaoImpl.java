package me.wbf.hospital.dao.impl;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import me.wbf.hospital.dao.UserDao;
import me.wbf.hospital.entity.User;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @author Baofeng.Wu
 * @since 1.0.0
 */
public class UserDaoImpl implements UserDao {
    static QueryRunner queryRunner = new QueryRunner(new ComboPooledDataSource());
    static BeanProcessor bean = new GenerousBeanProcessor();
    static RowProcessor processor = new BasicRowProcessor(bean);

    @Override
    public User findByUsername(String username) {
        String querySql = "SELECT * FROM tb_user WHERE username = ?";
        User user = null;
        try {
            user = queryRunner.query(querySql, new BeanHandler<>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String querySql = "SELECT * FROM tb_user WHERE email = ?";
        User user = null;
        try {
            user = queryRunner.query(querySql, new BeanHandler<>(User.class), email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        boolean result = false;
        String insertSql = "INSERT INTO tb_user(username,password,name,email) VALUES(?,?,?,?)";
        try {
            int affectedRows = queryRunner.update(insertSql, user.getUsername(), user.getPassword(), user.getName(), user.getEmail());
            result = affectedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
