package pers.me.ad.search.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pers.me.ad.search.vo.feature.DistrictFeature;
import pers.me.ad.search.vo.feature.FeatureRelation;
import pers.me.ad.search.vo.feature.ItFeature;
import pers.me.ad.search.vo.feature.KeywordFeature;
import pers.me.ad.search.vo.media.AdSlot;
import pers.me.ad.search.vo.media.App;
import pers.me.ad.search.vo.media.Device;
import pers.me.ad.search.vo.media.Geo;

import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    //媒体方的请求标识
    private String mediaId;
    //请求的基本信息
    private RequestInfo requestInfo;
    //匹配信息
    private FeatureInfo featureInfo;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestInfo{
        private String requestId;

        private List<AdSlot> adSlots;

        private App app;

        private Geo geo;

        private Device device;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FeatureInfo{
        private KeywordFeature keywordFeature;
        private DistrictFeature districtFeature;
        private ItFeature itFeature;
        //默认是and如果不填
        private FeatureRelation relation = FeatureRelation.AND;
    }

}
