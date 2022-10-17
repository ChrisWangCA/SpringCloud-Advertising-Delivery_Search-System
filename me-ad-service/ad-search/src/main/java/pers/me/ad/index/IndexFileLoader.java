package pers.me.ad.index;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import pers.me.ad.dump.DConstant;
import pers.me.ad.dump.table.*;
import pers.me.ad.handler.AdLevelDataHandler;
import pers.me.ad.mysql.constant.OpType;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-16
 */
@Component
@DependsOn("dataTable") //dataTable是一个bean，依赖于dataTable
public class IndexFileLoader {

    @PostConstruct //在构造函数之后执行
    public void init(){
        List<String> adPlanStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN)
        );
        adPlanStrings.forEach(p -> AdLevelDataHandler.handlerLevel1(
                JSON.parseObject(p, AdPlanTable.class), OpType.ADD
        ));
        List<String> adCreativeStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE)
        );
        adCreativeStrings.forEach(c -> AdLevelDataHandler.handlerLevel2(
                JSON.parseObject(c, AdCreativeTable.class), OpType.ADD
        ));
        List<String> adUnitString = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT)
        );
        adUnitString.forEach(u -> AdLevelDataHandler.handlerLevel3(
                JSON.parseObject(u, AdUnitTable.class), OpType.ADD
        ));
        List<String> adCreativeUnitString = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT)
        );
        adCreativeUnitString.forEach(cu -> AdLevelDataHandler.handlerLevel3(
                JSON.parseObject(cu, AdCreativeUnitTable.class), OpType.ADD
        ));
        List<String> adUnitDistrictStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_DISTRICT)
        );
        adUnitDistrictStrings.forEach(ud -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(ud, AdUnitDistrictTable.class), OpType.ADD
        ));
        List<String> adUnitItStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_IT)
        );
        adUnitItStrings.forEach(ui -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(ui, AdUnitItTable.class), OpType.ADD
        ));
        List<String> adUnitKeywordStrings = loadDumpData(
                String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT_KEYWORD)
        );
        adUnitKeywordStrings.forEach(uk -> AdLevelDataHandler.handlerLevel4(
                JSON.parseObject(uk, AdUnitKeywordTable.class), OpType.ADD
        ));
    }

    //读取数据文件并转成list
    private List<String> loadDumpData(String fileName){
        try(BufferedReader br = Files.newBufferedReader(
                Paths.get(fileName)
        )){
            return br.lines().collect(Collectors.toList());
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage());
        }
    }

}
