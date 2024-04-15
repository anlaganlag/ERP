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
public class ReturnOrderParm {
    /**
     * 退货订单状态:1待处理2已退款3已重发4已完成5已作废
     */
    private Integer status;
    /**
     * 订单编号，多个用英文逗号隔开，最大支持10个
     */
    private String platformOrderIds;


    /**
     * 创建时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDateStart;

    /**
     * 创建时间结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDateEnd;

    /**
     * 当前页数，默认1
     */
    private  int page=1;

    /**
     * 每页返回条数 |mcms默认初始值100
     */
    private  int rowsPerPage=100;



}
