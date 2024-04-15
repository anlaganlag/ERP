package com.tadpole.cloud.operationManagement.modular.shopEntity.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShop;
import com.tadpole.cloud.operationManagement.api.shopEntity.entity.TbComShopDataDown;
import com.tadpole.cloud.operationManagement.api.shopEntity.model.result.TbComShopDataDownResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 资源-店铺数据下载管理;(Tb_Com_Shop_Data_Down)--数据库访问层
 * @author : LSY
 * @date : 2023-8-11
 */
@Mapper
public interface TbComShopDataDownMapper  extends BaseMapper<TbComShopDataDown>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TbComShopDataDownResult> selectByPage(IPage<TbComShopDataDownResult> page , @Param(Constants.WRAPPER) Wrapper<TbComShopDataDown> wrapper);

     /**
      * 更新數據
      */
     @Update("MERGE INTO TB_COM_SHOP_DATA_DOWN t1  " +
             "USING (  " +
             "    SELECT c.ELE_PLATFORM_NAME ,c.SHOP_NAME_SIMPLE ,c.COUNTRY_CODE ,c.SHOP_NAME ,c.SELLER_ID  ,c.AUTH_STATUS ,c.SHOP_DATA_DOWN_TASK   " +
             "    FROM TB_COM_SHOP  c   " +
             "      WHERE c .SHOP_NAME IN ( SELECT TCSDD .SHOP_NAME  FROM TB_COM_SHOP_DATA_DOWN tcsdd WHERE TCSDD .CREATE_TASK  !=2 )  " +
             ")  t2  " +
             "ON (t1.ELE_PLATFORM_NAME  = t2.ELE_PLATFORM_NAME AND t1.SHOP_NAME_SIMPLE=t2.SHOP_NAME_SIMPLE AND t1.SHOP_NAME =t2.SHOP_NAME AND t1.COUNTRY_CODE =t2.COUNTRY_CODE )  " +
             "WHEN MATCHED THEN   " +
             "UPDATE SET T1.SELLER_ID = T2.SELLER_ID ,T1.AUTH_STATUS = T2.AUTH_STATUS, T1 .CREATE_TASK = DECODE(NVL(t2.SHOP_DATA_DOWN_TASK,0),0,-1,1,1,-1) WHERE   t1.CREATE_TASK  !=2")
    void updateShopDataDown();

     @Select("SELECT * FROM TB_COM_SHOP tcs WHERE TCS .SHOP_NAME NOT IN ( " +
             " SELECT DISTINCT  SHOP_NAME  FROM TB_COM_SHOP_DATA_DOWN   " +
             ")")
    List<TbComShop> getShopHistoryDataTask();

    @Select("SELECT * FROM TB_COM_SHOP tcs ")
    List<TbComShop> getAllShopList();

}