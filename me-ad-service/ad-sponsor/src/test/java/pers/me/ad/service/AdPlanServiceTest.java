package pers.me.ad.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pers.me.ad.Application;
import pers.me.ad.exception.AdException;
import pers.me.ad.serivce.IAdPlanService;
import pers.me.ad.vo.AdPlanGetRequest;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE) //不需要启动web环境
public class AdPlanServiceTest {

    @Autowired
    private IAdPlanService planService;

    @Test
    public void testGetAdPlan() throws AdException{
        System.out.println(planService.getAdPlanByIds(new AdPlanGetRequest(15L, Collections.singletonList(10L))));
    }

}
