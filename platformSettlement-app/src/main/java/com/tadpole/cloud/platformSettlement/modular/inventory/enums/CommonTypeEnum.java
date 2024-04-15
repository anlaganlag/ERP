package com.tadpole.cloud.platformSettlement.modular.inventory.enums;

import lombok.Getter;

/**
 * @author: gal
 * @description: 公用枚举类
 * @date: 2022/1/22
 */
@Getter
public enum CommonTypeEnum {
  Amazon("AZ", "Amazon"),
  ORG("ORG","ORG"),
  ORG_CODE("INVENTORY_ORGANIZATION_CODE","INVENTORY_ORGANIZATION_CODE"),
  WARE("WAREHOUSE_NAME","WAREHOUSE_NAME"),
  WARE_CODE("WAREHOUSE_CODE","WAREHOUSE_CODE"),
  SALES_ORG("SALES_ORG", "SALES_ORGANIZATION"),
  SALES_ORG_CODE("SALES_ORG_CODE", "SALES_ORGANIZATION_CODE");


  /**
   * 枚举值
   */
  private String code;

  /**
   * 枚举名称
   */
  private String name;

  CommonTypeEnum(String code, String name) {
    this.code = code;
    this.name = name;
  }

  public static CommonTypeEnum getEnum(String value) {
    CommonTypeEnum resultEnum = null;
    CommonTypeEnum[] enumAry = CommonTypeEnum.values();
    for (int i = 0; i < enumAry.length; i++) {
      if (enumAry[i].getCode().equals(value)) {
        resultEnum = enumAry[i];
        break;
      }
    }
    return resultEnum;
  }

}











