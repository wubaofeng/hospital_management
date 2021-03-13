package me.wbf.hospital.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Baofeng.Wu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicine {
    private Integer id;
    private String picture;
    private Float inPrice;
    private Float salPrice;
    private String name;
    private Integer type;
    private String simpleDescription;
    private Integer qualityDate;
    private String detailedDescription;
    private String productFirm;
    private String readme;
    private String remark;
}
