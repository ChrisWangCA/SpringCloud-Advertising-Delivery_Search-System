package pers.me.ad.index;

import lombok.Getter;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */

@Getter
public enum DateLevel {

    LEVEL2("2","level2"),
    LEVEL3("3","level3"),
    LEVEL4("4","level4");


    private String level;
    private String desc;

    DateLevel(String level,String desc){
        this.level = level;
        this.desc = desc;
    }

}
