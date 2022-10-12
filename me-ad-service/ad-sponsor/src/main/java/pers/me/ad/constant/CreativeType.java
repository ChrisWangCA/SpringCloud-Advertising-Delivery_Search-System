package pers.me.ad.constant;

import lombok.Getter;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@Getter
public enum CreativeType {

    IMAGE(1, "image"),
    VIDEO(2, "video"),
    TEXT(3, "text");

    private int type;
    private String desc;
    CreativeType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
