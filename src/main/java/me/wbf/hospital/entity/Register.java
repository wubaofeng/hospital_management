package me.wbf.hospital.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Baofeng.Wu
 */
@Data
@NoArgsConstructor
public class Register {
    private Integer id;
    private String name;
    private String idCard;
    /**
     * 社保卡号
     */
    private String socialSecurityNumber;
    private Float registerMoney;
    private String phone;
    private Integer isPay;
    private Integer sex;
    private Integer age;
    private Integer consultation;
    private Integer department;
    private Integer doctorId;
    private Long registerTime;
    private Integer status;
    private String remark;

    private Doctor doctor;


    public Register(Integer id, String name, String idCard, String socialSecurityNumber, Float registerMoney, String phone, Integer isPay, Integer sex, Integer age, Integer consultation, Integer department, Integer doctorId, Long registerTime, Integer status, String remark) {
        this.id = id;
        this.name = name;
        this.idCard = idCard;
        this.socialSecurityNumber = socialSecurityNumber;
        this.registerMoney = registerMoney;
        this.phone = phone;
        this.isPay = isPay;
        this.sex = sex;
        this.age = age;
        this.consultation = consultation;
        this.department = department;
        this.doctorId = doctorId;
        this.registerTime = registerTime;
        this.status = status;
        this.remark = remark;
    }
}
