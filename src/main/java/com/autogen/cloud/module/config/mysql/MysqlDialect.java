package com.autogen.cloud.module.config.mysql;

/**
 * Created by Spector on 2017/6/1.
 */
public enum MysqlDialect {

    Integer("int", 11, 0),
    Long("bigint", 20, 0),
    String("varchar", 256, 0),
    Float("float", 0, 0),
    Double("double", 0, 0),
    BigDecimal("decimal", 16, 2),
    Date("datetime", 0, 0);
//    Text("TEXT", 0, 0)

    /**
     * 字段类型
     */
    public String columnType;
    /**
     * 长度
     */
    public int length;
    /**
     * 小数点
     */
    public int decimalPoints;

    MysqlDialect(String columnType, int length, int decimalPoints) {
        this.columnType = columnType;
        this.length = length;
        this.decimalPoints = decimalPoints;
    }

}
