package com.autogen.cloud.module.service.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Spector on 2017/6/6.
 */
@Mapper
public interface TableMapper {

    @Select({
            "select TABLE_NAME from information_schema.`TABLES` " +
                    "where TABLE_SCHEMA=#{databaseName};"
    })
    public List<String> findTables(@Param("databaseName")String databaseName);

    @Insert({"${tableSchema}"})
    public void createTable(@Param("tableSchema") String tableSchema);

    @Select({
            "select COLUMN_NAME as 'columnName', COLUMN_TYPE as 'columnType', COLUMN_KEY as 'columnKey', EXTRA as 'extra' from information_schema.`COLUMNS` " +
                    "where TABLE_SCHEMA=#{databaseName} and TABLE_NAME=#{tableName}"
    })
    public List<FieldEntity> queryTableColumn(@Param("databaseName") String databaseName, @Param("tableName") String tableName);
}
