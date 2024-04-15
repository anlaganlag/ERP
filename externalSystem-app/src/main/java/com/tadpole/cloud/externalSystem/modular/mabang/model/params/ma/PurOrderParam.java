package com.tadpole.cloud.externalSystem.modular.mabang.model.params.ma;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurOrderParam {
    /**
     * 1.待处理 2.配货中 3.已发货 4.已完成 5.已作废 6.所有未发货 7.所有非未发货 默认配货中订单
     */
    private String flag;


    /**
     * 最后修改时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operTimeStart;


    /**
     * 最后修改时间结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date operTimeEnd;


    /**
     * 创建时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;


    /**
     * 创建时间结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;



    /**
     * 采购单号
     */
    private String groupId;


    /**
     * 自定义单号
     */
    private String orderBillNO;

    /**
     * 当前页数，默认1
     */
    private  int page=1;

    /**
     * 供应商名称
     */
    private String providerName;


    /**
     * 关联的订单号，支持模糊查询
     */
    private String platformOrderId;


}
