package me.wbf.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DO,管理员信息
 *
 * @author Baofeng.Wu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    /**
     * 用户名，登录用
     */
    private String username;
    private String password;
    /**
     * 名字：称呼或真实姓名
     */
    private String name;
    private String email;
}
