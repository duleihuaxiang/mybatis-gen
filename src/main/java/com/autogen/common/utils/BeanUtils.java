package com.autogen.common.utils;


import com.autogen.common.base.exception.BeanCopyException;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {
    private static PropertyDescriptor getPropertyDescriptor(PropertyDescriptor[] pds, PropertyDescriptor ref,
            boolean isStrict) {
        if (ref.getDisplayName().equals("class")) {
            return null;
        }

        for (PropertyDescriptor pd : pds) {
            if (isStrict) {
                if (pd.equals(ref)) {
                    return pd;
                }
            } else {
                if (ref.getPropertyType().equals(pd.getPropertyType()) && pd.getName().equals(ref.getName())) {
                    return pd;
                }
            }
        }
        return null;
    }


    public static void copyProperties(Object fromObj, Object toObj) throws BeanCopyException {
        copyProperties(fromObj, toObj, false);
    }

    public static void copyProperties(Object fromObj, Object toObj, boolean ignoreNull) throws BeanCopyException {
        if (fromObj == null || toObj == null) {
            return;
        }
        
        Class<? extends Object> fromClass = fromObj.getClass();
        Class<? extends Object> toClass = toObj.getClass();
        boolean isStrict = (fromClass == toClass);

        try {
            BeanInfo fromBean = Introspector.getBeanInfo(fromClass);
            BeanInfo toBean = Introspector.getBeanInfo(toClass);

            final PropertyDescriptor[] toPds = toBean.getPropertyDescriptors();
            final PropertyDescriptor[] fromPds = fromBean.getPropertyDescriptors();

            for (PropertyDescriptor toPd : toPds) {
                PropertyDescriptor fromPd = getPropertyDescriptor(fromPds, toPd, isStrict);
                if (fromPd != null && fromPd.getDisplayName().equals(toPd.getDisplayName())) {
                    Method writeMethod = toPd.getWriteMethod();
                    Method readMethod = fromPd.getReadMethod();
                    if (writeMethod != null && readMethod != null) {
                        Object param = readMethod.invoke(fromObj, (Object[]) null);
                        if (ignoreNull && param == null) {
                            continue;
                        }
                        writeMethod.invoke(toObj, param);
                    }
                }
            }
        } catch (Exception e) {
            throw new BeanCopyException(e);
        }
    }

    public static <T> T copyProperties(Object from, Class<T> toClass) throws BeanCopyException {
        if (from == null) {
            return null;
        }

        T to;
        try {
            to = toClass.newInstance();
            copyProperties(from, to);
        } catch (InstantiationException e) {
            throw new BeanCopyException(e);
        } catch (IllegalAccessException e) {
            throw new BeanCopyException(e);
        }
        return to;
    }

    public static <T> List<T> copyListProperties(Collection<? extends Object> fromList, Class<T> toClass) throws BeanCopyException {
        if (fromList == null) {
            return null;
        }

        List<T> result = new ArrayList<T>(fromList.size());
        for (Object from : fromList) {
            T to = copyProperties(from, toClass);
            result.add(to);
        }
        return result;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     *
     * @param type
     *            要转化的类型
     * @param map
     *            包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws Exception
     */
    public static Object map2Bean(Class<?> type, Map<String, Object> map) throws Exception {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (map.containsKey(propertyName)) {
                Method method = descriptor.getWriteMethod();
                if (method != null && method.getParameterTypes().length == 1) {
                    Object value = map.get(propertyName);
                    if(value != null) {
                        Class<?> parClass = method.getParameterTypes()[0];
                       if(parClass == Long.class) {
                            value = Long.valueOf(String.valueOf(value));
                        }else if(parClass == Integer.class) {
                            value = Integer.valueOf(String.valueOf(value));
                        }else if(parClass == Double.class) {
                            value = Double.valueOf(String.valueOf(value));
                        }else if(parClass == Date.class) {

                        }
                        method.invoke(obj, new Object[] {value});
                    }
                }
            }
        }
        return obj;
    }

    /**
     * 将一个 JavaBean 对象转化为一个 Map
     *
     * @param bean
     *            要转化的JavaBean 对象
     * @return 转化出来的 Map 对象
     *             如果分析类属性失败
     * @throws Exception
     */
    public static Map<String, Object> bean2Map(Object bean) throws Exception {
        if(bean == null || bean instanceof Map) {
            // 为空 exception
            return (Map<String, Object>) bean;
        }

        Map<String, Object> returnMap = new HashMap<String, Object>();
        Class<?> type = bean.getClass();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                if (readMethod != null) {
                    Object result = readMethod.invoke(bean, new Object[0]);
                    returnMap.put(propertyName, result);
                }
            }
        }
        return returnMap;
    }

    public static List<Map<String, Object>> bean2Map2(List<Object> beans) throws Exception {
        List<Map<String, Object>> returnMaps = new ArrayList<Map<String, Object>>();
        for(Object bean : beans) {
            returnMaps.add(bean2Map(bean));
        }
        return returnMaps;
    }
}
