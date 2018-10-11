package com.autogen.cloud.module.service.totable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
@Service
public class EntityPackage {

    private Map<String, List<Field>> tableField = new HashMap<>();

    @Autowired
    private TableGeneral tableGeneral;

    private Class<?> clazz;

    public void loadAllFields(){

        List<Field> fields = new ArrayList<>();
        getAllFields(clazz, fields);
        fields = fields.stream().distinct().collect(Collectors.toList());

        tableGeneral.setFields(fields);
        tableGeneral.setTableName(toTableName());
        tableGeneral.genralTable();
    }

    private void getAllFields(Class<?> clazz, List<Field> fields){
        Class<?> superclass = clazz.getSuperclass();
        Collections.addAll(fields, clazz.getDeclaredFields());

        if(!superclass.equals(Object.class)){
            getAllFields(superclass, fields);
        }
    }

    private String toTableName(){
        String className = clazz.getSimpleName();
        return camel2Underline(className);
    }

    /**
     * 驼峰法转下划线
     * @param line 源字符串
     * @return 转换后的字符串
     */
    public static String camel2Underline(String line){
        if(line==null||"".equals(line)){
            return "";
        }
        line=String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
        StringBuffer sb=new StringBuffer();
        Pattern pattern= Pattern.compile("[A-Z]([a-z\\d]+)?");
        Matcher matcher=pattern.matcher(line);
        while(matcher.find()){
            String word=matcher.group();
            sb.append(word.toLowerCase());
            sb.append(matcher.end()==line.length()?"":"_");
        }
        return sb.toString();
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
