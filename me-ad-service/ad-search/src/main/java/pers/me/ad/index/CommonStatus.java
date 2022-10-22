package pers.me.ad.index;

import lombok.Data;
import lombok.Getter;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-20
 */
@Getter
public enum CommonStatus {

    VALID(1,"valid"),
    INVALID(0,"invalid");

    private Integer status;
    private String desc;

    CommonStatus(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

}
