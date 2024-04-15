package com.tadpole.cloud.externalSystem.modular.mabang.mapper;

import com.tadpole.cloud.externalSystem.modular.mabang.entity.MabangWarehouse;
import com.tadpole.cloud.externalSystem.modular.mabang.model.params.MabangWarehouseParam;
import com.tadpole.cloud.externalSystem.modular.mabang.model.result.MabangWarehouseResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* <p>
    * 马帮仓库列表 Mapper 接口
    * </p>
*
* @author lsy
* @since 2022-06-09
*/
public interface MabangWarehouseMapper extends BaseMapper<MabangWarehouse> {

    Page<MabangWarehouseResult> findPageBySpec(@Param("page") Page page, @Param("paramCondition") MabangWarehouseParam paramCondition);

//        @Select("SELECT a.FNUMBER,b.FNAME FROM T_BD_Stock a,T_BD_STOCK_L b WHERE a.ISSPECIALWAREHOUSE= 1 AND a.FUSEORGID= 100269 AND a.FSTOCKID= b.FSTOCKID")
    List<Map<String, String>> k3MabangWarehouse();

    List<String> queryNames();

    List<MabangWarehouseResult> warehouseList(@Param("paramCondition") MabangWarehouseParam paramCondition);

    List<MabangWarehouseResult> getNewWarehouseByName( String warehouse);
}
