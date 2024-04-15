package com.tadpole.cloud.externalSystem.modular.k3.mode;


import cn.stylefeng.guns.cloud.model.validator.BaseValidatingParam;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* <p>
    * 店铺销量库存表
    * </p>
*
* @author lsy
* @since 2023-02-17
*/
@Data
public class LoginInfo implements Serializable, BaseValidatingParam {

    private BigDecimal id;


    @ApiModelProperty("账套id")
    private String dbid;


    @ApiModelProperty("用户名")
    private String username;


    @ApiModelProperty("应用id")
    private String appid;


    @ApiModelProperty("签名")
    private String signeddata;


    @ApiModelProperty("时间戳")
    private String timestamp;


    @ApiModelProperty("语言")
    private String lcid;


    @ApiModelProperty("origintype")
    private String origintype;


    @ApiModelProperty("entryrole")
    private String entryrole;


    @ApiModelProperty("formid")
    private String formid;


    @ApiModelProperty("formtype")
    private String formtype;


    @ApiModelProperty("pkid")
    private String pkid;


    @ApiModelProperty("otherargs")
    private String otherargs;


    @ApiModelProperty("openmode")
    private String openmode;


}
