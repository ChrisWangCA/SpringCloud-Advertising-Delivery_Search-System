package me.pers.ad.search;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.me.ad.SearchApplication;
import pers.me.ad.search.ISearch;
import pers.me.ad.search.vo.SearchRequest;
import pers.me.ad.search.vo.feature.DistrictFeature;
import pers.me.ad.search.vo.feature.FeatureRelation;
import pers.me.ad.search.vo.feature.ItFeature;
import pers.me.ad.search.vo.feature.KeywordFeature;
import pers.me.ad.search.vo.media.AdSlot;
import pers.me.ad.search.vo.media.App;
import pers.me.ad.search.vo.media.Device;
import pers.me.ad.search.vo.media.Geo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SearchApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SearchTest {
    @Autowired
    private ISearch search;

    @Test
    public void testFetchAds() {
        SearchRequest request = new SearchRequest();
        request.setMediaId("pers-ad");

        request.setRequestInfo(new SearchRequest.RequestInfo(
                "pers-ad",
                Collections.singletonList(new AdSlot("ad-x",1,1080,720, Arrays.asList(1,2),100)),
                buildExampleApp(),
                buildExampleGeo(),
                buildExampleDevice()
        ));
        request.setFeatureInfo(buildExampleFeatureInfo(
                Arrays.asList("宝马","大众"),
                Collections.singletonList(new DistrictFeature.ProvinceAndCity("湖南省","长沙市")),
                Arrays.asList("爬山","旅游"),
                FeatureRelation.OR
        ));
        System.out.println(JSON.toJSONString(request));
        System.out.println(JSON.toJSONString(search.fetchAds(request)));
    }

    private App buildExampleApp() {
        return new App("pers-ad", "pers-ad", "pers-ad", "pers-ad");
    }

    private Geo buildExampleGeo() {
        return new Geo((float) 1.1, (float) 1.1, "pers-ad", "pers-ad");
    }

    private Device buildExampleDevice() {
        return new Device(
                "iphone", "xxx", "127.0.0.1", "x", "1080 720", "1080 720", "1234567890"
        );
    }

    private SearchRequest.FeatureInfo buildExampleFeatureInfo(List<String> keywords,
                                                              List<DistrictFeature.ProvinceAndCity> provinceAndCities,
                                                              List<String> its,
                                                              FeatureRelation relation) {
        return new SearchRequest.FeatureInfo(new KeywordFeature(), new DistrictFeature(), new ItFeature(), relation);
    }


    }


