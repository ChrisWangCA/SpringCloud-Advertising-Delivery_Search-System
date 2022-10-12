package pers.me.ad.constant;

import lombok.Getter;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
@Getter
public enum CreativeMaterialType {
    //图片
    JPG(1, "jpg"),
    BMP(2, "bmp"),
    //视频
    MP4(3,"mp4"),
    AVI(4,"avi"),
    //文本
    TXT(5,"txt");

    private int type;
    private String desc;
    CreativeMaterialType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

}
