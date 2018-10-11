package com.autogen.cloud.module.service.mapper;

import java.lang.reflect.Field;

/**
 * Created by Spector on 2017/6/7.
 */
public class ModifyEntity {

    private Field field;
    /**
     * 是否在某个字段之后
     */
    private String afterColumnName;
    /**
     * 是否是新建字段, moren : add
     */
    private boolean isNewColumn = true;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getAfterColumnName() {
        return afterColumnName;
    }

    public void setAfterColumnName(String afterColumnName) {
        this.afterColumnName = afterColumnName;
    }

    public boolean isNewColumn() {
        return isNewColumn;
    }

    public void setNewColumn(boolean newColumn) {
        isNewColumn = newColumn;
    }
}
