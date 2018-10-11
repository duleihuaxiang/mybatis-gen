package com.autogen.cloud.module.service.totable;

import com.autogen.cloud.module.config.Constants;
import com.autogen.cloud.module.config.DataSourceConfiguration;
import com.autogen.cloud.module.config.mysql.MysqlDialect;
import com.autogen.cloud.module.service.mapper.FieldEntity;
import com.autogen.cloud.module.service.mapper.ModifyEntity;
import com.autogen.cloud.module.service.mapper.TableMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spector on 2017/6/2.
 */
@Service
public class TableGeneral {

    private static final Logger logger = LoggerFactory.getLogger(TableMapper.class);

    @Autowired
    private DataSourceConfiguration dataSourceConfiguration;
    @Autowired
    private TableMapper tableMapper;

    private String tableName;
    private List<Field> fields;
    private String databaseName;

    private boolean checkTableExists() {
        List<String> tables = getTables();
        if (null != tables && !tables.isEmpty()) {
            for (String table : tables) {
                if (table.equals(tableName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void genralTable() {
        if (checkTableExists()) {
            logger.info("表{} 已存在，schema做更新处理", tableName);
            final String updateSchemaScript = updateSchema();
            if(null != updateSchemaScript){
                tableMapper.createTable(updateSchemaScript);
            }

        } else {
            logger.info("表{} 暂不存在，schema做新增处理", tableName);
            String createSchemaScript = createSchema();
            if(null != createSchemaScript){
                tableMapper.createTable(createSchemaScript);
            }
        }
    }

    /**
     * 创建表
     *
     * @return
     */
    private String createSchema() {
        if (null != fields && !fields.isEmpty()) {
            int size = fields.size();
            StringBuffer schemaTitle = new StringBuffer("CREATE TABLE `");
            schemaTitle.append(tableName).append("` (");

            StringBuffer schemaBody = new StringBuffer();
            boolean primaryKey = false;

            for (int i = 0; i < size; i++) {
                Field field = fields.get(i);
                String type = field.getType().getSimpleName();
                String name = field.getName();

                //主键的构建
                MysqlDialect mysqlDialect = MysqlDialect.valueOf(type);
                if (name.equals(Constants.DEFAULT_PRIMARY_ID)) {
                    primaryKey = true;
                    schemaTitle.append("  `").append(name).append("` ").append(mysqlDialect.columnType)
                            .append("(").append(mysqlDialect.length).append(")")
                            .append(" NOT NULL AUTO_INCREMENT COMMENT '主键ID',");
                    continue;
                }
                buildCreateSchemaOtherTypeBody(schemaBody, type, name, mysqlDialect);
            }

            buildCreateSchemaTextBody(schemaBody, primaryKey);
            schemaTitle.append(schemaBody);

            logger.info("表{}的创建脚本: {}", tableName, schemaTitle.toString());
            return schemaTitle.toString();
        }

        logger.warn("表：{} 实体字段不存在，当前未生成!", tableName);
        return null;
    }

    /**
     * 创建表 - 其他类型的构建
     * @param schemaBody
     * @param type
     * @param name
     * @param mysqlDialect
     */
    private void buildCreateSchemaOtherTypeBody(StringBuffer schemaBody, String type, String name, MysqlDialect mysqlDialect) {
        schemaBody.append("  `").append(name).append("` ").append(mysqlDialect.columnType);
        if (!type.equals(MysqlDialect.Date.name())) {
            schemaBody.append("(").append(mysqlDialect.length);
            if (type.equals(MysqlDialect.BigDecimal.name()) ||
                    type.equals(MysqlDialect.Float.name()) ||
                    type.equals(mysqlDialect.Double.name())) {
                schemaBody.append(", ").append(mysqlDialect.decimalPoints);
            }
            schemaBody.append(")");
        }
        if (type.equals(MysqlDialect.String.name())) {
            schemaBody.append("CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }
        schemaBody.append(" DEFAULT NULL COMMENT '', ");
    }

    /**
     * 创建表 - 文本类型的构建
     * @param schemaBody
     * @param primaryKey
     */
    private void buildCreateSchemaTextBody(StringBuffer schemaBody, boolean primaryKey) {
        schemaBody.append(" `last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,");
        if (primaryKey) {
            schemaBody.append(" PRIMARY KEY (`")
                    .append(Constants.DEFAULT_PRIMARY_ID).append("`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';");
        } else {
            String body = schemaBody.substring(0, schemaBody.length() - 1);
            schemaBody.setLength(0);
            schemaBody.append(body).append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';");
        }
    }

    private String updateSchema() {
        /**
         * select DATA_TYPE, COLUMN_TYPE, COLUMN_KEY, EXTRA from information_schema.`COLUMNS` where TABLE_SCHEMA='yit_local_magento' and TABLE_NAME='user';
         * 1、比较字段名称是否存存，不存在的，则新增
         */
        List<ModifyEntity> modifyEntityList = diffEntityWithTable();
        if (null != modifyEntityList && !modifyEntityList.isEmpty()) {
            StringBuffer updateSchema = new StringBuffer();
            updateSchema.append("ALTER TABLE `" + tableName + "`");

            int count = 0;
            int size = modifyEntityList.size();
            for (int i = 0; i < size; i++) {
                ModifyEntity modify = modifyEntityList.get(i);
                alterAddColumnSchema(updateSchema, modify);

                count++;
                if(count == size){
                    updateSchema.append(";");
                }else{
                    updateSchema.append(",");
                }
            }

            if(updateSchema.length() != 0){
                logger.info("表{}的更新脚本: {}", tableName, updateSchema);
                return updateSchema.toString();
            }
        }
        return null;
    }

    /**
     * 实体对象字段差异
     * @return
     */
    private List<ModifyEntity> diffEntityWithTable() {
        List<FieldEntity> columns = getColumns();
        if (null != columns && !columns.isEmpty()) {
            List<ModifyEntity> fieldList = moreField(columns);
            return  fieldList;
        } else {
            if (null != fields && !fields.isEmpty()) {
                int size = fields.size();

                List<ModifyEntity> modifyEntityList = new ArrayList<>();
                for (int i = 0; i < size; i++) {
                    Field field = fields.get(i);

                    ModifyEntity modify = new ModifyEntity();
                    modify.setField(field);
                    if (i > 0) {
                        modify.setAfterColumnName(fields.get(i-1).getName());
                    }

                    modifyEntityList.add(modify);
                }
                return modifyEntityList;
            }
        }

        return null;
    }

    /**
     * 新增字段脚本补充
     *
     * @param updateAddSchema
     * @param modify
     */
    private void alterAddColumnSchema(StringBuffer updateAddSchema, ModifyEntity modify) {
        Field field = modify.getField();
        String type = field.getType().getSimpleName();
        String name = field.getName();

        MysqlDialect mysqlDialect = MysqlDialect.valueOf(type);
        if (name.equals(Constants.DEFAULT_PRIMARY_ID)) {
            if(modify.isNewColumn()){
                updateAddSchema.append(" ADD COLUMN ");
            }else{
                updateAddSchema.append(" MODIFY ");
            }

            updateAddSchema.append("`").append(name)
                    .append("` ")
                    .append(mysqlDialect.columnType)
                    .append("(")
                    .append(mysqlDialect.length);


            updateAddSchema.append(")")
                    .append(" DEFAULT '0'");

            if(modify.isNewColumn()){
                updateAddSchema.append(modify.getAfterColumnName() == null ? "" : " AFTER `" + modify.getAfterColumnName()+"`");
            }
            return;
        }

        if (!type.equals(MysqlDialect.Date.name())) {
            if(modify.isNewColumn()){
                updateAddSchema.append(" ADD COLUMN ");
            }else{
                updateAddSchema.append(" MODIFY ");
            }

            updateAddSchema.append("`").append(name)
                    .append("` ")
                    .append(mysqlDialect.columnType)
                    .append("(")
                    .append(mysqlDialect.length);

            if (type.equals(MysqlDialect.BigDecimal.name()) ||
                    type.equals(MysqlDialect.Float.name()) ||
                    type.equals(mysqlDialect.Double.name())) {
                updateAddSchema.append(", ").append(mysqlDialect.decimalPoints);
            }
            updateAddSchema.append(")");
        }

        if (type.equals(MysqlDialect.String.name())) {
            updateAddSchema.append(" CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci");
        }

        updateAddSchema.append(" DEFAULT NULL ");
        if(modify.isNewColumn()){
            updateAddSchema.append(modify.getAfterColumnName() == null ? "" : " AFTER `" + modify.getAfterColumnName()+"`");
        }
    }


    private List<ModifyEntity> moreField(List<FieldEntity> columns) {
       List<ModifyEntity> fieldList = new ArrayList<>();

        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            String name = field.getName();
            String type = field.getType().getSimpleName();

            ModifyEntity modify = new ModifyEntity();
            boolean contain = false;
            for (int j = 0; j < columns.size(); j++) {
                FieldEntity cl = columns.get(j);
                if (name.equals(cl.getColumnName())) {

                    //字段类型是否修改
                    MysqlDialect mysqlDialect = MysqlDialect.valueOf(type);
                    if(!cl.getColumnType().startsWith(mysqlDialect.columnType)){
                        modify.setNewColumn(false);
                        modify.setField(field);

                        if (i != 0) {
                            modify.setAfterColumnName(fields.get(i - 1).getName());
                        }
                        fieldList.add(modify);
                    }

                    contain = true;
                    break;
                }
            }
            if (!contain) {
                if (i != 0) {
                    modify.setAfterColumnName(fields.get(i - 1).getName());
                }
                modify.setField(field);
                fieldList.add(modify);
            }
        }

        return fieldList;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
    }

    /**
     * 库
     *
     * @return
     */
    private String getDatabaseName() {
        return dataSourceConfiguration.getDatabaseName();
    }

    /**
     * 表
     *
     * @return
     */
    public List<String> getTables() {
        return tableMapper.findTables(getDatabaseName());
    }

    /**
     * 列
     *
     * @return
     */
    public List<FieldEntity> getColumns() {
        return tableMapper.queryTableColumn(getDatabaseName(), tableName);
    }
}
