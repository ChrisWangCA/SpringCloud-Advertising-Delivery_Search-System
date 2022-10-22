package pers.me.ad.search.impl;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import pers.me.ad.index.CommonStatus;
import pers.me.ad.index.DataTable;
import pers.me.ad.index.ad_unit.AdUnitIndex;
import pers.me.ad.index.ad_unit.AdUnitObject;
import pers.me.ad.index.creative.CreativeIndex;
import pers.me.ad.index.creative.CreativeObject;
import pers.me.ad.index.creative_unit.CreativeUnitIndex;
import pers.me.ad.index.district.UnitDistrictIndex;
import pers.me.ad.index.interest.UnitItIndex;
import pers.me.ad.index.keyword.UnitKeywordIndex;
import pers.me.ad.search.ISearch;
import pers.me.ad.search.vo.SearchRequest;
import pers.me.ad.search.vo.SearchResponse;
import pers.me.ad.search.vo.feature.DistrictFeature;
import pers.me.ad.search.vo.feature.FeatureRelation;
import pers.me.ad.search.vo.feature.ItFeature;
import pers.me.ad.search.vo.feature.KeywordFeature;
import pers.me.ad.search.vo.media.AdSlot;

import java.util.*;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-19
 */

@Slf4j
@Service
public class SearchImpl implements ISearch {

    public SearchResponse fallback(SearchRequest request, Throwable throwable){
        return null;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public SearchResponse fetchAds(SearchRequest request) {

        //请求的广告位信息
        List<AdSlot> adSlots = request.getRequestInfo().getAdSlots();
        //三个feature
        KeywordFeature keywordFeature = request.getFeatureInfo().getKeywordFeature();
        DistrictFeature districtFeature = request.getFeatureInfo().getDistrictFeature();
        ItFeature itFeature = request.getFeatureInfo().getItFeature();

        FeatureRelation relation = request.getFeatureInfo().getRelation();

        //构造响应对象
        SearchResponse response = new SearchResponse();
        Map<String,List<SearchResponse.Creative>> adSlot2Ads = response.getAdSlot2Ads();
        for (AdSlot adSlot : adSlots) {
            Set<Long> targetUnitIdSet;
            //根据流量类型获取初始的AdUnit
            Set<Long> adUnitIdSet = DataTable.of(
                    AdUnitIndex.class).match(adSlot.getPositionType());
            if (relation == FeatureRelation.AND){
                filterKeywordFeature(adUnitIdSet,keywordFeature);
                filterDistrictFeature(adUnitIdSet,districtFeature);
                filterItFeature(adUnitIdSet,itFeature);
                targetUnitIdSet = adUnitIdSet;
            }else{
                targetUnitIdSet = getOrRelationUnitIds(adUnitIdSet,keywordFeature,districtFeature,itFeature);
            }
            List<AdUnitObject> unitObjects = DataTable.of(AdUnitIndex.class).fetch(targetUnitIdSet);
            filterAdUnitAndPlanStatus(unitObjects,CommonStatus.VALID);
            List<Long> adIds = DataTable.of(CreativeUnitIndex.class).selectAds(unitObjects);
            List<CreativeObject> creatives = DataTable.of(CreativeIndex.class).fetch(adIds);

            filterCreativeByAdSlot(creatives,adSlot.getWidth(),adSlot.getHeight(),adSlot.getType());

            adSlot2Ads.put(adSlot.getAdSlotCode(),buildCreativeResponse(creatives));

        }

        log.info("fetchAds: {}-{}",JSON.toJSONString(request), JSON.toJSONString(response));

        return response;
    }
    private Set<Long> getOrRelationUnitIds(Set<Long> adUnitIdSet,KeywordFeature keywordFeature,DistrictFeature districtFeature,ItFeature itFeature){
        if (CollectionUtils.isEmpty(adUnitIdSet)){
            return Collections.emptySet();
        }
        Set<Long> keywordUnitIds = new HashSet<>(adUnitIdSet);
        Set<Long> districtUnitIds = new HashSet<>(adUnitIdSet);
        Set<Long> itUnitIds = new HashSet<>(adUnitIdSet);

        filterKeywordFeature(keywordUnitIds,keywordFeature);
        filterDistrictFeature(districtUnitIds,districtFeature);
        filterItFeature(itUnitIds,itFeature);

        return new HashSet<>(CollectionUtils.union(CollectionUtils.union(keywordUnitIds,districtUnitIds),itUnitIds));
    }

    private void filterKeywordFeature(Collection<Long> adUnitIds, KeywordFeature keywordFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(keywordFeature.getKeywords())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(
                    UnitKeywordIndex.class).match(adUnitId,keywordFeature.getKeywords()));
        }
    }

    private void filterDistrictFeature(Collection<Long> adUnitIds, DistrictFeature districtFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
        if (CollectionUtils.isNotEmpty(districtFeature.getDistricts())){
            CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(
                    UnitDistrictIndex.class).match(adUnitId,districtFeature.getDistricts()));
        }
    }

    private void filterItFeature(Collection<Long> adUnitIds, ItFeature itFeature){
        if (CollectionUtils.isEmpty(adUnitIds)){
            return;
        }
       if (CollectionUtils.isNotEmpty(itFeature.getIts())){
           CollectionUtils.filter(adUnitIds,adUnitId -> DataTable.of(
                   UnitItIndex.class).match(adUnitId,itFeature.getIts()));
       }
    }

    private void filterAdUnitAndPlanStatus(List<AdUnitObject> unitObjects, CommonStatus status){
        if(CollectionUtils.isEmpty(unitObjects)){
            return;
        }
        CollectionUtils.filter(unitObjects,
                object -> object.getUnitStatus().equals(status.getStatus())
                        && object.getAdPlanObject().getPlanStatus().equals(status.getStatus()));
    }

    private void filterCreativeByAdSlot(List<CreativeObject> creatives,Integer width,Integer height,List<Integer> type){
        if (CollectionUtils.isEmpty(creatives)){
            return;
        }
        CollectionUtils.filter(creatives,creative -> creative.getAuditStatus().equals(CommonStatus.VALID.getStatus())
                && creative.getWidth().equals(width)
                && creative.getHeight().equals(height)
                && type.contains(creative.getType()));
    }

    private List<SearchResponse.Creative> buildCreativeResponse(List<CreativeObject> creatives){
        if (CollectionUtils.isEmpty(creatives)){
            return Collections.emptyList();
        }
        CreativeObject randomObject = creatives.get(
                Math.abs(new Random().nextInt()) % creatives.size()
        );
        return Collections.singletonList(SearchResponse.convert(randomObject));
    }

}
