package com.meng.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.meng.define.CommonDefine.*;


/**
 * common utils
 *
 * @author : sunyuecheng
 */
public final class CommonUtils {

    /**
     * copy bean value
     *
     * @param srcObj :
     * @param clazz  :
     * @param <T>    :
     * @return T :
     */
    public static <T> T copyBeanValue(Object srcObj, Class<T> clazz) {
        try {
            BeanCopier beanCopier = BeanCopier.create(srcObj.getClass(), clazz, false);
            T obj = clazz.newInstance();

            beanCopier.copy(srcObj, obj, null);

            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * copy bean value list
     *
     * @param srcObjList  :
     * @param srcClazz    :
     * @param targetClazz :
     * @param <T>         :
     * @param <E>         :
     * @return List<T> :
     */
    public static <T, E> List<T> copyBeanValueList(List<E> srcObjList, Class<E> srcClazz, Class<T> targetClazz) {
        try {
            BeanCopier beanCopier = BeanCopier.create(srcClazz, targetClazz, false);

            List<T> objList = new ArrayList<>();
            for (Object srcObj : srcObjList) {
                T obj = targetClazz.newInstance();
                beanCopier.copy(srcObj, obj, null);

                objList.add(obj);
            }

            return objList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * format exception info
     *
     * @param e :
     * @return String :
     */
    public static String formatExceptionInfo(Throwable e) {
        if (e == null) {
            return null;
        }

        String logTraceSwitch = System.getenv(SYSTEM_PROPERTY_LOG_TRACE_SWITCH_KEY);
        if (StringUtils.isEmpty(logTraceSwitch) || !Boolean.TRUE.toString().equals(logTraceSwitch)) {
            return e.getMessage();
        }

        StringBuffer sb = new StringBuffer();

        sb.append(e);
        sb.append("\nTrace:\n");

        StackTraceElement[] trace = e.getStackTrace();
        for (StackTraceElement ele : trace) {
            sb.append("\t").append(ele).append("\n");
        }

        return sb.toString();
    }

    /**
     * get current day begin time
     *
     * @return long :
     */
    public static long getCurrentDayBeginTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    /**
     * get current day end time
     *
     * @return long :
     */
    public static long getCurrentDayEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, DAY_HOURS);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private CommonUtils() {
    }

}
