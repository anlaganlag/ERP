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
public class OrderParm {
    /**
     * 1.待处理 2.配货中 3.已发货 4.已完成 5.已作废 6.所有未发货 7.所有非未发货 默认配货中订单
     */
    private String status;
    /**
     * 订单编号，多个用英文逗号隔开，最大支持10个
     */
    private String platformOrderIds;
    /**
     * 店铺名称
     */
    private String shopName;
    /**
     * 最后修改时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date updateTimeEnd;

    /**
     * 付款时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date paidtimeStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date paidtimeEnd;
    /**
     * 创建时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDateStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDateEnd;

    /**
     * 发货时间开始时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expressTimeStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date expressTimeEnd;


    /**
     * 1.正常订单 2.异常订单 3.全部订单 默认正常订单
     */
    private String canSend;

    /**
     * 当前页数，默认1
     */
    private  int page=1;



    /**
     * 偏移量，不传默认第一页，传值根据回参字段 nextCursor
     */

    private String cursor;


    /**
     * 是否查询所有状态 1 是 ,2否. 默认传2
     */

    private String allstatus;




    /**
     * 返回发货状态 自发货203已发货，204已完成，205已作废，202,201待发货：
     */

    private String extendFileds;



    /**
     * 分页查询数；默认100，上限支持1000
     */

    private String maxRows;


    /**
     * 店铺ID
     */

    private String shopId;




    /**
     * 平台交易号，最多10个
     */

    private String salesRecordNumbers;


    /**
     * 基于当前日期查询多少天内的数据默认值1
     */
    private Integer queryDay;






}
