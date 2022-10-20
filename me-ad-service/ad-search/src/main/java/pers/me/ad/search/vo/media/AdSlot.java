package pers.me.ad.search.vo.media;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdSlot {

    //广告位编码
    private String adSlotCode;

    //流量类型
    private Integer positionType;

    //广告位的宽和高
    private Integer width;
    private Integer height;

    //广告物料的类型，图片，视频
    private List<Integer> type;

    //最低出价
    private Integer minCpm;

}
