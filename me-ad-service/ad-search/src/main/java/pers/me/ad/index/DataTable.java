package pers.me.ad.index;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.stereotype.Component;
import pers.me.ad.index.creative_unit.CreativeUnitIndex;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chris Wang
 * @version 1.0
 * @date 2022-10-14
 */
@Component
//ApplicationContextAware
//PriorityOrdered 用于指定初始化顺序
public class DataTable implements ApplicationContextAware, PriorityOrdered {

    private static ApplicationContext applicationContext;

    public static final Map<Class,Object> dataTableMap = new ConcurrentHashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DataTable.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return PriorityOrdered.HIGHEST_PRECEDENCE; //最高优先级 值越小优先级越高
    }


    @SuppressWarnings("all")
    public static <T> T of(Class<T> clazz){
        T instance = (T) dataTableMap.get(clazz);
        if(null != instance){
            return instance;
        }
        dataTableMap.put(clazz,bean(clazz));
        return (T) dataTableMap.get(clazz);
    }

    @SuppressWarnings("all")
    private static <T> T bean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    @SuppressWarnings("all")
    private static <T> T bean(Class clazz){
        return (T) applicationContext.getBean(clazz);
    }


}
