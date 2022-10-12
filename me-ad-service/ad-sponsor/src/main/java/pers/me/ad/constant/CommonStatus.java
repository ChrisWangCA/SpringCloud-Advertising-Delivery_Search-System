package pers.me.ad.constant;

import lombok.Getter;

import javax.validation.Valid;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-11
 */

@Getter
public enum CommonStatus { //枚举类 用于表示状态     //枚举类的构造方法默认是private的

    VALID(1, "Valid"),
    INVALID(0, "Invalid");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
