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
public class App {

    //应用编码
    private String appCode;
    //应用名称
    private String appName;
    //应用包名
    private String packageName;
    //应用的活动
    private String activityName;

}
