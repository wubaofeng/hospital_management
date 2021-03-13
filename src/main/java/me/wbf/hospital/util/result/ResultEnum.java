package me.wbf.hospital.util.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Baofeng.Wu
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum  ResultEnum {
    SUCCESS(0,"success"),
    FAIL(1,"fail");
    private Integer code;
    private String msg;
}
