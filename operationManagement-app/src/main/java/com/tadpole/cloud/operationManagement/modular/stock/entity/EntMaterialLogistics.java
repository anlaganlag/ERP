package com.tadpole.cloud.operationManagement.modular.stock.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("material_logistics")
public class EntMaterialLogistics implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId(value = "id")
  private String id;

  @TableField("logistics_mode")
  @Excel(name = "物流方式")
  private String logisticsMode;

  @TableField("platform")
  @Excel(name = "平台")
  private String platform;

  @TableField("area")
  @Excel(name = "区域")
  private String area;

  @TableField("department")
  @Excel(name = "事业部")
  private String department;

  @TableField("team")
  @Excel(name = "Team")
  private String team;

  @TableField(value = "material_code")
  @Excel(name = "物料编码")
  private String materialCode;


  @TableField(value = "status")
  private String status;

  @TableField(value = "create_date")
  private String createDate;

  @TableField(value = "update_by", fill = FieldFill.UPDATE)
  private String updateUser;

  @TableField(value = "update_at")
  private String updateTime;




  @TableField(value = "create_by", fill = FieldFill.INSERT)
  private String createUser;




  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLogisticsMode() {
    return logisticsMode;
  }

  public void setLogisticsMode(String logisticsMode) {
    this.logisticsMode = logisticsMode;
  }

  public String getPlatform() {
    return platform;
  }

  public void setPlatform(String platform) {
    this.platform = platform;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getTeam() {
    return team;
  }

  public void setTeam(String team) {
    this.team = team;
  }

  public String getMaterialCode() {
    return materialCode;
  }

  public void setMaterialCode(String materialCode) {
    this.materialCode = materialCode;
  }

  public String getCreateDate() {
    return createDate;
  }

  public void setCreateDate(String createDate) {
    this.createDate = createDate;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public String getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(String updateTime) {
    this.updateTime = updateTime;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }


  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EntMaterialLogistics that = (EntMaterialLogistics) o;
    return getPlatform().equals(that.getPlatform()) && getArea().equals(that.getArea())
        && getDepartment().equals(that.getDepartment()) && getTeam().equals(that.getTeam())
        && getMaterialCode().equals(that.getMaterialCode());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getPlatform(), getArea(), getDepartment(), getTeam(), getMaterialCode());
  }
}
