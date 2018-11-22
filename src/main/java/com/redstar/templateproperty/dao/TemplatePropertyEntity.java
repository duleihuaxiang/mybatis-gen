package com.redstar.templateproperty.dao;

import java.io.Serializable;
import java.util.Date;

public class TemplatePropertyEntity implements Serializable {
    private Long templatePropertyId;

    private String templatePropertyCode;

    private String templatePropertyName;

    private String templatePropertyValue;

    private Long templateId;

    private String remark;

    private Integer displayOrder;

    private Integer version;

    private Long createUserId;

    private String createUserName;

    private Date createTime;

    private Long updateUserId;

    private String updateUserName;

    private Date updateTime;

    private Date lastUpdate;

    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public Long getTemplatePropertyId() {
        return templatePropertyId;
    }

    public void setTemplatePropertyId(Long templatePropertyId) {
        this.templatePropertyId = templatePropertyId;
    }

    public String getTemplatePropertyCode() {
        return templatePropertyCode;
    }

    public void setTemplatePropertyCode(String templatePropertyCode) {
        this.templatePropertyCode = templatePropertyCode == null ? null : templatePropertyCode.trim();
    }

    public String getTemplatePropertyName() {
        return templatePropertyName;
    }

    public void setTemplatePropertyName(String templatePropertyName) {
        this.templatePropertyName = templatePropertyName == null ? null : templatePropertyName.trim();
    }

    public String getTemplatePropertyValue() {
        return templatePropertyValue;
    }

    public void setTemplatePropertyValue(String templatePropertyValue) {
        this.templatePropertyValue = templatePropertyValue == null ? null : templatePropertyValue.trim();
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}