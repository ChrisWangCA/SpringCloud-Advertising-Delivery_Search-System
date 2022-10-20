package pers.me.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device {
    //设备id
    private String deviceCode;

    // mac地址
    private String mac;

    // ip地址
    private String ip;

    // 机型编码
    private String model;

    // 分辨率尺寸
    private String resolution;

    // 屏幕尺寸
    private String screenSize;

    // 设备序列号
    private String serialName;

}
