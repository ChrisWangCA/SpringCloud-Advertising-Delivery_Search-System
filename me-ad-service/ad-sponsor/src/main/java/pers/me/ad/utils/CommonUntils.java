package pers.me.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import pers.me.ad.exception.AdException;

import java.util.Date;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-12
 */
public class CommonUntils {
    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };


    //md5加密
    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    //将字符串时间转换为Date类型
    public static Date parseStringDate(String dateString) throws AdException{
        try{
            return DateUtils.parseDate(
                    dateString,parsePatterns
            );
        }catch (Exception e){
            throw new AdException(e.getMessage());
        }
    }
}
