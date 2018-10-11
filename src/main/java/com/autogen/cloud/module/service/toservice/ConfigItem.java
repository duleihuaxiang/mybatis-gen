package com.autogen.cloud.module.service.toservice;

public class ConfigItem {
//    pkg: com.autogen.doctor
//    domain.className: Doctor
//    primaryKeyField: doctorId
//    uriPath: /doctor

    public String pkg;
    public String domain;
    public String primaryKeyField;
    public String uriPath;
    public String dao;
    
    public String getPkg() {
        return pkg;
    }
    public void setPkg(String pkg) {
        this.pkg = pkg;
    }
    public String getDomain() {
        return domain;
    }
    public void setDomain(String domain) {
        this.domain = domain;
    }
    public String getPrimaryKeyField() {
        return primaryKeyField;
    }
    public void setPrimaryKeyField(String primaryKeyField) {
        this.primaryKeyField = primaryKeyField;
    }
    public String getUriPath() {
        return uriPath;
    }
    public void setUriPath(String uriPath) {
        this.uriPath = uriPath;
    }

    public String getDao() {
        return dao;
    }

    public void setDao(String dao) {
        this.dao = dao;
    }

    @Override
    public String toString() {
        return "ConfigItem{" +
                "pkg='" + pkg + '\'' +
                ", domain='" + domain + '\'' +
                ", primaryKeyField='" + primaryKeyField + '\'' +
                ", uriPath='" + uriPath + '\'' +
                ", dao='" + dao + '\'' +
                '}';
    }
}
