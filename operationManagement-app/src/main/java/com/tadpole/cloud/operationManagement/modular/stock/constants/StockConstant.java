package com.tadpole.cloud.operationManagement.modular.stock.constants;

import java.math.BigDecimal;

public class StockConstant {

    /**
     * 数据批量处理条数
     */
    public static final int BATCH_SIZE = 500;

    public static String ORACLE_NVL_DEFAULT = "0";

    /**
     * redis 获取业务编号
     */
    public static  String  SYNC_K3_PURCHASE_ORDER_KEY="SYNC_K3_PURCHASE_ORDER_KEY";

    /**
     * 审核业务类型
     */
    public static  int  VERIF_BIZ_TYPE_PMC=30;//pmc
    public static  int  VERIF_BIZ_TYPE_PLAN=20;//计划部
    public static  int  VERIF_BIZ_TYPE_DEPARTMENT=10;//事业部

    /**
     * 同步状态
     */
    public static String SYNC_WAIT = "-1";
    public static String SYNC_SUCESS = "1";
    public static String SYNC_FAIL= "0";

    /**
     * 待审核0，审核通过1，不通过2
     */
    public static String VERIFY_WAIT = "0";
    public static String VERIFY_SUCESS = "1";
    public static String VERIFY_FAIL= "2";

    /**
     * 供应商key
     */
    public static String SUPPLIER_KEY = "SUPPLIER_KEY";

    /**
     * K3采购申请单 前缀
     */
    public static String PURCHASE_ORDER_PREFIX = "CGSQ";

    /**
     * 超时备货处理  Y通过，或者备货   N：不通过，或者不备货
     */
    public static String OVERTIME_ACTION_YES = "Y";
    public static String OVERTIME_ACTION_NO = "N";


    /**
     * 超时备货s数据困状态：  1备货   0：不备货
     */
    public static BigDecimal ORACLE_OVERTIME_ACTION_YES = BigDecimal.ONE;
    public static BigDecimal ORACLE_OVERTIME_ACTION_NO = BigDecimal.ZERO;

    /**
     *  "0:待申请,1:已提交,2:已申请,3:超时不备货
     */
    public static int OPER_STOCK_STATUS_WAIT = 0;
    public static int OPER_STOCK_STATUS_COMIT = 1;
    public static int OPER_STOCK_STATUS_APPLY = 2;
    public static int OPER_STOCK_STATUS_NO = 3;

    /**
     *  "0:待审核,1:已提交,2:已申请,3:超时不备货
     */
    public static String TEAM_STOCK_STATUS_WAIT = "0";
    public static String TEAM_STOCK_STATUS_COMIT = "1";
    public static String TEAM_STOCK_STATUS_APPLY = "2";
    public static String TEAM_STOCK_STATUS_NO = "3";

    /**
     *  1:所有都提交，0，还未都提交
     */

    public static BigDecimal ALL_COMIT_YES = BigDecimal.ONE;
    public static BigDecimal ALL_COMIT_NO = BigDecimal.ZERO;


/** 采购订单状态:值域{"0:采购申请单待审核"/"1:不备货"/"2:待计划部审批"/"3:计划未通过"/"4:待PMC审批"/"5:PMC未通过"/"6:已通过(且同步k3成功)，7:已通过(同步k3失败)"}默认值：待审核
 */
    public static int ORDER_STATUS_WAIT = 0;
    public static int ORDER_STATUS_N0_STOCK = 1;
    public static int ORDER_STATUS_PLAN_WAIT = 2;
    public static int ORDER_STATUS_PLAN_NO = 3;
    public static int ORDER_STATUS_PMC_WAIT = 4;
    public static int ORDER_STATUS_PMC_NO = 5;
    public static int ORDER_STATUS_PMC_YES = 6;
    public static int ORDER_STATUS_PMC_YES_SYNC_FAIL = 7;

    //

    public static String BLACKLIST_MAT = "物料黑名单";
    public static String BLACKLIST_AREA = "区域黑名单";
    public static String BLACKLIST_SITE = "小站点黑名单";
    public static String BLACKLIST_QTY_ZERO = "数据为0不做备货推荐";
    public static String BLACKLIST_OBSOLETE = "建议淘汰产品";





}
