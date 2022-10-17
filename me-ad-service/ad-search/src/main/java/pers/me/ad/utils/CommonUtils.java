package pers.me.ad.utils;

import java.util.Map;
import java.util.function.Supplier;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-13
 */
public class CommonUtils {

    public static <K,V> V getOrCreate(K key, Map<K,V> map, Supplier<V> factory){
        return map.computeIfAbsent(key,k -> factory.get()); //computeIfAbsent()方法是在map中不存在key时，才会调用factory.get()方法
    }
    //构建连字符函数
    public static String stringConcat(String... args){
        StringBuilder result = new StringBuilder();
        for(String arg:args){
            result.append(arg);
            result.append("-");
        }
        //去掉最后一个连字符
        result.deleteCharAt(result.length()-1);
        return result.toString();
    }

}
