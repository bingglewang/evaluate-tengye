package com.spring.mpvue.springbootmpvue.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;

import com.google.common.collect.Lists;
import org.slf4j.LoggerFactory;


public class BeanutilsCopy<T> {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(BeanutilsCopy.class);

    /**
     * apacheCopyProperties(单个对象拷贝)
     *
     * @param @param dest
     * @param @param orig
     * @return void
     * @throws
     * @Title: apacheCopyProperties
     */
    public static void apacheCopyProperties(Object dest, Object orig) {
        try {
            BeanUtils.copyProperties(dest, orig);
        } catch (Exception e) {
            log.error("apacheCopyProperties 异常：" + e);
        }

    }

    /**
     * mapList(拷贝集合信息)
     *
     * @param @param  sourceList
     * @param @param  destinationClass
     * @param @return
     * @return List<T>
     * @throws
     * @Title: mapList
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> List<T> CopyList(Collection sourceList, Class<T> destinationClass) {
        DozerBeanMapper dozer = new DozerBeanMapper();
        List destinationList = Lists.newArrayList();
        for (Iterator i$ = sourceList.iterator(); i$.hasNext(); ) {
            Object sourceObject = i$.next();
            Object destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    @SuppressWarnings("unchecked")
    public static <T, D> T populateTbyDBySpring(D sourceObj, Class<T> clazz) {
        if (sourceObj == null) {
            return null;
        }
        Object t = null;
        try {
            t = clazz.newInstance();
        } catch (IllegalAccessException e) {
            log.error("自动转换失败", e);
        } catch (InstantiationException exx) {
            log.error("自动转换失败", exx);
        }
        org.springframework.beans.BeanUtils.copyProperties(sourceObj, t);

        return (T) t;

    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T, D> List<T> populateTListbyDListBySpring(List<D> sourceObjs, Class<T> clazz) {
        if (sourceObjs == null) {
            return null;
        }
        int len = sourceObjs.size();
        List ts = new ArrayList(len);
        Object t = null;
        for (int i = 0; i < len; i++) {
            Object d = sourceObjs.get(i);
            t = populateTbyDBySpring(d, clazz);
            ts.add(t);
        }
        return ts;
    }

}



