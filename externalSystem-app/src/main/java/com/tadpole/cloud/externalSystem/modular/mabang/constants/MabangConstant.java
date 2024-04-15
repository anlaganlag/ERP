package com.tadpole.cloud.externalSystem.modular.mabang.constants;

public class MabangConstant {

    /**
     * redis 获取业务编号
     */
    public static  String  SYNC_K3_SALEOUT_ORDER_KEY="SYNC_K3_SALEOUT_ORDER_KEY";

    /**
     * K3销售出库单 前缀
     */
    public static String SALEOUT_ORDER_PREFIX = "XSCK";


    /**
     * 马帮K3销售出库单 前缀
     */
    public static String SALEOUT_ORDER_PREFIX_MB = "MBXSCK";


    /**
     * K3销售出库webapi  fromId
     */
    public static String K3_FROM_ID_SAL_OUTSTOCK = "SAL_OUTSTOCK";



    /**
     * K3同步创建物料--等待中
     */
    public static String K3_SYNC_CREATE_MAT_WAIT = "K3_SYNC_CREATE_MAT_WAIT";



    /**
     * K3同步物料刷数据--等待中
     */
    public static String K3_SYNC_MAT_REFRESH_WAIT = "K3_SYNC_MAT_REFRESH_WAIT";


    /**
     * 同步跨组织调拨单到K3
     */
    public static String K3_SYNC_CROSS_TRANSFER = "K3_SYNC_CROSS_TRANSFER";


    /**
     * erp物料同步
     */
    public static String ERP_MAT_SYNC = "ERP_MAT_SYNC";



    /**
     * K3同步存储过程--等待中
     */
    public static String K3_SYNC_PROCEDURE_WAIT = "K3_SYNC_PROCEDURE_WAIT";

    /**
     * k3调拨单同步到MCMS系统--作业中
     */
    public static String K3_SYNC_MCMS_TRANSFER_WORK = "K3_SYNC_MCMS_TRANSFER_WORK";

    /**
     * k3指定仓库所有物料可用数量，销售出库到马帮erp
     */
    public static String K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT = "K3_SYNC_MCMS_WAREHOUSE_AVAILABLE_QYT";

    /**
     * k3调拨单同步到MCMS系统--等待中
     */
    public static String K3_SYNC_MCMS_TRANSFER_WAIT = "K3_SYNC_MCMS_TRANSFER_WAIT";

    /**
     * MCMS系统调拨单同步到马帮--作业中
     */
    public static String MCMS_SYNC_MABANG_TRANSFER_WORK = "MCMS_SYNC_MABANG_TRANSFER_WORK";

    /**
     *  MCMS系统调拨单同步到马帮--等待中
     */
    public static String MCMS_SYNC_MABANG_TRANSFER_WAIT = "MCMS_SYNC_MABANG_TRANSFER_WAIT";

    /**
     *  K3其他入库billno
     */
    public static String K3_OTHER_IN_ORDER_NO = "K3_OTHER_IN_ORDER_NO";




    public static String IS_DELETE_YES = "1";
    public static String IS_DELETE_NO = "0";

    public static String RESULT_SUCCESS_CODE = "200";

    public static String SYNC_SUCCESS = "1";
    public static String SYNC_FAIL = "0";
    public static String SYNC_INIT = "-1";


    public static int MIXED_BUSINESS_YES = 1;//混合业务
    public static int MIXED_BUSINESS_NO = 0;//非混合业务

    /** K3调拨方向(0:其他仓库-->小平台仓库（入库增加库存），1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)，2：包含前两种的混合业务（调拨单里面既有入库，又有出库操作的业务） */
    public static int TRANSFER_DIRECTION_OUT_IN = 2;//2：包含前两种的混合业务（调拨单里面既有入库，又有出库操作的业务）
    public static int TRANSFER_DIRECTION_IN = 0;//0:其他仓库-->小平台仓库（入库增加库存）
    public static int TRANSFER_DIRECTION_OUT = 1;//1：小平台仓库-->其他仓库（不包含小平台自身仓库，出库减少库存)


    /** 该直接调拨单本次同步记录是否触发了反审核：0未触发，1已触发 */
    public static int ANTI_AUDIT_DO = 1;
    public static int ANTI_AUDIT_UN = 0;


    /** 反审核触发的操作（默认0：无变化，1：采购订单作废，2：反向创建分仓调拨单） */
    public static int ANTI_AUDIT_ACTION_NO_CHANGE = 0;
    public static int ANTI_AUDIT_ACTION_PURCHASE_ORDER_DEL = 1;
    public static int ANTI_AUDIT_ACTION_REVERSE_ALLOCATION = 2;



    //分仓调拨 指定调出仓库
    public static String ALLOCATION_WAREHOUSE_OUT = "虚拟调出仓库";



    //已完成订单 	订单状态 1.待处理 2.配货中 3.已发货 4.已完成 5.已作废
    public static String  PENDING  = "1";
    public static String PREPARING_STOCK = "2";
    public static String EXPRESSED = "3";
    public static String FINISHED = "4";
    public static String CANCEL = "5";
    public static String ALL_STATUS = "1";



    //马帮物料状态 	商品状态：1、自动创建；2、待开发；3、正常；4、清仓；5、停止销售
    public static String MAT_STATUS_NORMAL = "3";
    public static String MAT_STATUS_STOP = "5";



    public static Integer BATCH_SIZE = 500;

    //B2B业务常量


    /**
     * B2B订单关闭，操作人
     */
    public static String B2B_ORDER_CLOSE_BY_OPER = "业务关闭";//运营关闭订单
    public static String B2B_ORDER_CLOSE_BY_FINANCE = "财务关闭";//财务关闭订单

    /**
     * 运营提交
     */
    public static String OPERATE_PAYMENT_DETAIL_STATUS_SAVE = "暂存";
    public static String OPERATE_PAYMENT_DETAIL_STATUS_SUBMIT = "已提交";

    /**
     * 财务确认付款明细
     */
    public static String FINANCE_PAYMENT_DETAIL_STATUS_UNCONFIRMED = "未确认";
    public static String FINANCE_PAYMENT_DETAIL_STATUS_CONFIRMED = "已确认";

    /**
     * 付款明细操作类型
     */
    public static Integer BIZ_TYPE_FINANCE_CONFIRM = -1; //财务确认更新汇总表数据
    public static Integer BIZ_TYPE_OPERATE_SUBMIT = 1;//运营提交更新汇总表数据


    /**
     * 付款明显在MC的状态
     */
    public static String PAYMENT_DETAIL_MC_STATUS_NORMAL = "未关闭";
    public static String PAYMENT_DETAIL_MC_STATUS_NORMAL_CLOSE = "正常关闭";
    public static String PAYMENT_DETAIL_MC_STATUS_BIZ_CLOSE = "业务关闭";
    public static String PAYMENT_DETAIL_MC_STATUS_FINANCE_CLOSE = "财务关闭";


    /**
     * 平台店铺收款-备注
     */
    public static String REMARK_PLAT_SHOP_ACCOUNT = "平台店铺收款";



    public static String SYNC_K3_AVAILABLE_QTY_TO_MABANG = "SYNC_K3_AVAILABLE_QTY_TO_MABANG";//"同步k3可用库存数量到马帮"






}
