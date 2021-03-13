package me.wbf.hospital.entity;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 医生信息表
 *
 * @author Baofeng.Wu
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    private Integer id;
    private String name;
    private String idCard;
    private String phone;
    /**
     * 0-女 / 1 - 男
     */
    private Integer sex;
    private Integer age;
    private Long birthday;
    private String email;
    /**
     * 1-急诊科 / 2-儿科 / 3-妇科 / 4-皮肤科 / 5-内分泌科 / 6-牙科
     */
    private Integer department;
    /**
     * 1-专科 / 2-本科 / 3-研究生 / 4-博士
     */
    private Integer education;
    private String remark;
}
